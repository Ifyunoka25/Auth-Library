package university.miva.designsystem.util

import timber.log.Timber

object LogUtil {
    private const val MAX_LOG = 3900

    fun longLog(
        tag: String,
        prefix: String = "",
        raw: String,
    ) {
        if (raw.isEmpty()) {
            Timber.tag(tag).d("$prefix<empty>")
            return
        }
        var i = 0
        while (i < raw.length) {
            val end = (i + MAX_LOG).coerceAtMost(raw.length)
            Timber.tag(tag).d("%s%s", prefix, raw.substring(i, end))
            i = end
        }
    }

    fun prettyOrRawJson(raw: String?): String {
        if (raw.isNullOrBlank()) return "<null>"
        return try {
            val trimmed = raw.trim()
            if (trimmed.startsWith("{")) {
                val obj = org.json.JSONObject(trimmed)
                obj.toString(2)
            } else if (trimmed.startsWith("[")) {
                val arr = org.json.JSONArray(trimmed)
                arr.toString(2)
            } else {
                raw
            }
        } catch (_: Throwable) {
            raw
        }
    }
}

// --- Pretty JSON helpers ---
fun String.isJsonObject() = trim().startsWith("{")

fun String.isJsonArray() = trim().startsWith("[")

fun prettyJsonOrRaw(raw: String?): String {
    return try {
        if (raw.isNullOrBlank()) return "<null>"
        when {
            raw.isJsonObject() -> org.json.JSONObject(raw).toString(2)
            raw.isJsonArray() -> org.json.JSONArray(raw).toString(2)
            else -> raw
        }
    } catch (_: Throwable) {
        raw ?: "<null>"
    }
}

// Log long text in chunks so Logcat doesn’t truncate
fun logLong(
    tag: String,
    header: String,
    text: String,
    chunk: Int = 3900,
) {
    Timber.tag(tag).d(header)
    var i = 0
    while (i < text.length) {
        Timber.tag(tag).d(text.substring(i, (i + chunk).coerceAtMost(text.length)))
        i += chunk
    }
}

fun prettyOrRawJson(raw: String?): String =
    try {
        when {
            raw.isNullOrBlank() -> "<null>"
            raw.trim().startsWith("{") -> org.json.JSONObject(raw).toString(2)
            raw.trim().startsWith("[") -> org.json.JSONArray(raw).toString(2)
            else -> raw
        }
    } catch (_: Throwable) {
        raw ?: "<null>"
    }

fun longTimber(
    tag: String,
    header: String,
    text: String,
    chunk: Int = 3900,
) {
    Timber.tag(tag).d(header)
    var i = 0
    while (i < text.length) {
        val end = (i + chunk).coerceAtMost(text.length)
        Timber.tag(tag).d(text.substring(i, end))
        i = end
    }
}
