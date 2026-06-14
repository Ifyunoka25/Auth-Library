package university.miva.designsystem.util

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import org.json.JSONObject
import timber.log.Timber
import java.io.File
import java.util.Locale

object FileUtils {
    fun videosDir(context: Context) = File(context.filesDir, "videos").apply { mkdirs() }

    fun pdfsDir(context: Context) = File(context.filesDir, "pdfs").apply { mkdirs() }

    fun tmpFile(
        context: Context,
        safeName: String,
    ) = File(videosDir(context), "$safeName.tmp")

    fun outFile(
        context: Context,
        safeName: String,
    ) = File(videosDir(context), "$safeName.mp4")

    fun encFile(
        context: Context,
        base: String,
    ) = File(videosDir(context), "$base.enc") // final encrypted

    fun safeName(raw: String) = raw.lowercase().replace("[^a-z0-9._-]".toRegex(), "_")

    fun masterKey(context: Context) =
        MasterKey
            .Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    /** Encrypt a plaintext file into an .enc file atomically. Deletes target if exists. */
    fun encryptFile(
        context: Context,
        plain: File,
        encOut: File,
    ) {
        if (!plain.exists()) error("Source not found: ${plain.path}")
        if (encOut.exists()) encOut.delete()

        val ef =
            EncryptedFile
                .Builder(
                    context,
                    encOut,
                    masterKey(context),
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB,
                ).build()

        plain.inputStream().use { input ->
            ef.openFileOutput().use { encOutStream ->
                input.copyTo(encOutStream)
                encOutStream.flush()
            }
        }
    }

    /** Decrypt an .enc file into a temp .mp4 in cache for playback; caller should delete later. */
    fun decryptToTempForPlayback(
        context: Context,
        encFile: File,
    ): File {
        val temp = File.createTempFile("play_", ".mp4", context.cacheDir)

        val ef =
            EncryptedFile
                .Builder(
                    context,
                    encFile,
                    masterKey(context),
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB,
                ).build()

        ef.openFileInput().use { encIn ->
            temp.outputStream().use { out -> encIn.copyTo(out) }
        }
        return temp
    }

    data class DownloadMeta(
        val uuid: String,
        val displayName: String,
        val url: String,
        val createdAt: Long,
        val courseId: Int,
    )

    fun metaFile(
        context: Context,
        base: String,
    ) = File(videosDir(context), "$base.meta.json")

    fun writeVideoMeta(
        context: Context,
        base: String,
        meta: DownloadMeta,
    ) {
        val file = metaFile(context, base)
        file.writeText(
            JSONObject()
                .apply {
                    put("uuid", meta.uuid)
                    put("displayName", meta.displayName)
                    put("url", meta.url)
                    put("createdAt", meta.createdAt)
                    put("courseId", meta.courseId)
                }.toString(),
        )
    }

    fun readMeta(
        context: Context,
        base: String,
    ): DownloadMeta? =
        runCatching {
            val file = JSONObject(metaFile(context, base).readText())
            DownloadMeta(
                uuid = file.getString("uuid"),
                displayName = file.getString("displayName"),
                url = file.getString("url"),
                createdAt = file.optLong("createdAt"),
                courseId = file.getInt("courseId"),
            )
        }.getOrNull()

    fun deleteQuietly(f: File): Long {
        if (!f.exists()) return 0L
        val size = f.length()
        runCatching { f.delete() }
        return if (f.exists()) 0L else size
    }

    fun pdfDisplayName(
        id: String,
        name: String,
    ) = "${safeName(id)}-${safeName(name)}.pdf"

    @RequiresApi(Build.VERSION_CODES.Q)
    fun insertPdfToDownloads(
        ctx: Context,
        displayName: String,
    ): Uri {
        val values =
            ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, displayName) // e.g. "1234-Module Intro.pdf"
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_DOWNLOADS}/Miva")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        return requireNotNull(
            ctx.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values),
        ) { "Failed to create MediaStore entry for PDF in Download/Miva" }
    }

    fun legacyDownloadsDir(): File {
        val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Miva")
        if (!dir.exists()) {
            val ok = dir.mkdirs()
            Timber.tag(PDF_TAG).d("[legacy] mkdirs(%s) -> %s", dir.absolutePath, ok)
        }
        return dir
    }

    private fun pdfNamePrefix(id: String) = "${safeName(id)}-"

    @RequiresApi(Build.VERSION_CODES.Q)
    fun findPdfInDownloadsQ(
        ctx: Context,
        id: String,
    ): Pair<Uri, Long>? {
        val projection =
            arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.SIZE,
                MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.MediaColumns.RELATIVE_PATH,
            )
        val sel =
            buildString {
                append("${MediaStore.MediaColumns.MIME_TYPE}=?")
                append(" AND ${MediaStore.MediaColumns.RELATIVE_PATH} LIKE ?")
                append(" AND ${MediaStore.MediaColumns.DISPLAY_NAME} LIKE ?")
            }
        val args =
            arrayOf(
                "application/pdf",
                "%${Environment.DIRECTORY_DOWNLOADS}/Miva%",
                "${pdfNamePrefix(id)}%", // e.g. "<uuid>-*.pdf"
            )

        ctx.contentResolver
            .query(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                projection,
                sel,
                args,
                null,
            )?.use { c ->
                val idIdx = c.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                val sizeIdx = c.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE)
                if (c.moveToFirst()) {
                    val rowId = c.getLong(idIdx)
                    val size = c.getLong(sizeIdx)
                    val uri = ContentUris.withAppendedId(MediaStore.Downloads.EXTERNAL_CONTENT_URI, rowId)
                    return uri to size
                }
            }
        return null
    }

    fun findPdfInDownloadsLegacy(id: String): Pair<Uri, Long>? {
        val prefix = pdfNamePrefix(id)
        val file =
            legacyDownloadsDir()
                .listFiles { f -> f.extension.equals("pdf", true) && f.name.startsWith(prefix) }
                ?.firstOrNull() ?: return null
        return Uri.fromFile(file) to file.length()
    }

    private val DEDUPE_SUFFIX = Regex("""\s\(\d+\)$""") // strips " (1)" etc before .pdf

    private fun stripIdAndExtension(fileName: String): String {
        val noExt = fileName.replace(Regex("\\.pdf$", RegexOption.IGNORE_CASE), "")
        val withoutId = noExt.substringAfter('-', noExt) // "123-Name" -> "Name"; else unchanged
        return DEDUPE_SUFFIX.replace(withoutId, "")
    }

    private fun toTitleCase(
        raw: String,
        locale: Locale = Locale.getDefault(),
    ): String =
        raw
            .replace('_', ' ')
            .replace(Regex("\\s+"), " ")
            .trim()
            .lowercase(locale)
            .split(' ')
            .filter { it.isNotBlank() }
            .joinToString(" ") { it.replaceFirstChar { ch -> ch.titlecase(locale) } }

    fun prettyPdfDisplayNameFromFileName(fileName: String): String = toTitleCase(stripIdAndExtension(fileName))
}

const val PDF_TAG = "PDFDL"

inline fun logD(
    id: String,
    step: String,
    detail: () -> String = { "" },
) {
    Timber.tag(PDF_TAG).d("[%s] %s %s", id, step, detail())
}

inline fun logE(
    id: String,
    step: String,
    t: Throwable? = null,
    detail: () -> String = { "" },
) {
    Timber.tag(PDF_TAG).e(t, "[%s] %s %s", id, step, detail())
}
