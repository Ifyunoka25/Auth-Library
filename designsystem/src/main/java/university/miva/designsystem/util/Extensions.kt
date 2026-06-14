package university.miva.designsystem.util

import Theme
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.util.Base64
import android.view.Window
import android.view.WindowManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import java.util.regex.Pattern

const val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?#&.])[A-Za-z\\d@\$!%*#?&.]{8,}$"

fun extractYouTubeId(url: String): String? {
    val pattern =
        "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"
    val compiledPattern = Pattern.compile(pattern)
    val matcher = compiledPattern.matcher(url)
    return if (matcher.find()) matcher.group() else null
}

val json =
    Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
        allowStructuredMapKeys = true
        serializersModule =
            SerializersModule {
                contextual(Uri::class, UriSerializer)
            }
    }

object UriSerializer : KSerializer<Uri> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Uri", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: Uri,
    ) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Uri = decoder.decodeString().toUri()
}

fun String.encodeToUrl(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())

fun String.decodeFromUrl(): String = URLDecoder.decode(this, StandardCharsets.UTF_8.toString())

inline fun <reified T> String.decodeJsonString(): T = json.decodeFromString(this)

inline fun <reified T> T.encodeToJson(): String = json.encodeToString(this)

fun String.toRoutePayload(): String =
    Base64.encodeToString(toByteArray(Charsets.UTF_8), Base64.URL_SAFE or Base64.NO_WRAP)

fun String.fromRoutePayload(): String = String(Base64.decode(this, Base64.URL_SAFE or Base64.NO_WRAP), Charsets.UTF_8)

fun String.toTitleCase(): String =
    lowercase().split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }

fun String.containsAnyKeyword(keywords: List<String>): Boolean =
    keywords.any { keyword -> this.contains(keyword, ignoreCase = true) }

fun <T> LazyListScope.gridItems(
    data: List<List<T>>,
    itemContent: @Composable (T) -> Unit,
) {
    data.forEachIndexed { index, rowItems ->
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.x7),
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        itemContent(item)
                    }
                }

                // If this row is not full, add empty placeholders
                repeat((rowItems.size until 4).count()) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // Add vertical spacing below every row except the last one
        if (index != data.lastIndex) {
            item {
                Spacer(modifier = Modifier.height(Theme.spacing.x3))
            }
        }
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier =
    composed {
        this.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = onClick,
        )
    }

fun Modifier.viewAllClickable(onClick: () -> Unit): Modifier =
    composed {
        this
            .clip(shape = RoundedCornerShape(Theme.spacing.x5))
            .clickable { onClick() }
            .padding(vertical = Theme.spacing.x2, horizontal = Theme.spacing.x3)
    }

@Composable
fun <T> GridItems(
    data: List<List<T>>,
    itemContent: @Composable (T) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.x3)) {
        data.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.x7),
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        itemContent(item)
                    }
                }

                // Fill remaining space in the row if less than 4 items
                repeat((rowItems.size until 4).count()) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

fun Int.formatWithCommas(): String = "%,d".format(this)

fun getNameInitials(name: String): String =
    name
        .split(" ")
        .mapNotNull { it.firstOrNull()?.uppercase() }
        .take(2)
        .joinToString("")

fun openExternalLink(
    context: Context,
    url: String,
) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
    context.startActivity(intent)
}

fun openGoogleMeet(
    context: Context,
    url: String,
) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri())

    intent.setPackage("com.google.android.apps.meetings")

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // If the Meet app is not installed, unset the package
        // and launch the intent again. This will open it in a browser.
        intent.setPackage(null)
        context.startActivity(intent)
    }
}

fun String.toReadableDateTime(
    zoneId: ZoneId = ZoneId.systemDefault(),
    locale: Locale = Locale.UK,
): String =
    runCatching {
        val zdt =
            try {
                Instant.parse(this).atZone(zoneId)
            } catch (_: Exception) {
                try {
                    ZonedDateTime.parse(this).withZoneSameInstant(zoneId)
                } catch (_: Exception) {
                    try {
                        OffsetDateTime.parse(this).atZoneSameInstant(zoneId)
                    } catch (
                        _: Exception,
                    ) {
                        LocalDateTime.parse(this).atZone(zoneId)
                    }
                }
            }

        fun ordinal(n: Int) =
            when {
                n % 100 in 11..13 -> "th"
                n % 10 == 1 -> "st"
                n % 10 == 2 -> "nd"
                n % 10 == 3 -> "rd"
                else -> "th"
            }

        val day = zdt.dayOfMonth
        val month = zdt.month.getDisplayName(TextStyle.SHORT, locale) // e.g., Jan
        val year = zdt.year
        val time = zdt.format(DateTimeFormatter.ofPattern("h:mm a", locale))

        "$day${ordinal(day)} $month $year, $time"
    }.getOrElse { this }

fun formatTimestamp(
    isoUtc: String,
    zoneId: ZoneId = ZoneId.of("Africa/Lagos"),
    locale: Locale = Locale.UK,
): String {
    val instant = Instant.parse(isoUtc)
    val zdt = instant.atZone(zoneId)

    fun ordinal(n: Int): String =
        when {
            n % 100 in 11..13 -> "th"
            n % 10 == 1 -> "st"
            n % 10 == 2 -> "nd"
            n % 10 == 3 -> "rd"
            else -> "th"
        }

    val dow = zdt.dayOfWeek.getDisplayName(TextStyle.SHORT, locale) // Mon
    val day = zdt.dayOfMonth // 7
    val month = zdt.month.getDisplayName(TextStyle.FULL, locale) // July
    val year = zdt.year // 2025
    val time = zdt.format(DateTimeFormatter.ofPattern("h:mm a", locale)) // 11:31 PM

    return "$dow, ${day}${ordinal(day)} $month $year, $time"
}

fun formatDaysTimestamp(
    isoUtc: String,
    zoneId: ZoneId = ZoneId.of("Africa/Lagos"),
    locale: Locale = Locale.UK,
): String {
    fun ordinal(n: Int): String =
        when {
            n % 100 in 11..13 -> "th"
            n % 10 == 1 -> "st"
            n % 10 == 2 -> "nd"
            n % 10 == 3 -> "rd"
            else -> "th"
        }

    return try {
        val instant = Instant.parse(isoUtc)
        val zdt = instant.atZone(zoneId)
        val date = zdt.toLocalDate()
        val today = LocalDate.now(zoneId)

        val daysAgo = ChronoUnit.DAYS.between(date, today).toInt()

        when (daysAgo) {
            0 -> {
                // Today → show time only
                zdt.format(DateTimeFormatter.ofPattern("h:mm a", locale))
            }
            1 -> {
                // Yesterday → label only (no time)
                "Yesterday"
            }
            in 2..6 -> {
                // Within last 7 days → day of week
                zdt.dayOfWeek.getDisplayName(TextStyle.FULL, locale) // e.g., "Monday, Tuesday, etc"
            }
            else -> {
                // 7+ days → full date with ordinal, no day name
                val d = zdt.dayOfMonth
                val month = zdt.month.getDisplayName(TextStyle.FULL, locale)
                val year = zdt.year
                "$d${ordinal(d)} $month $year"
            }
        }
    } catch (_: Exception) {
        // Fallback gracefully if parsing fails
        isoUtc
    }
}

fun formatRelativeDateFromTimestamp(timestamp: Long): String {
    val dateTime =
        Instant
            .ofEpochSecond(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

    val now = LocalDateTime.now()
    val today = now.toLocalDate()
    val yesterday = today.minusDays(1)
    val date = dateTime.toLocalDate()

    return when (date) {
        today -> "Today at ${dateTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH))}"
        yesterday -> "Yesterday at ${dateTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH))}"
        in today.minusDays(6)..yesterday -> // within the last week
            "${dateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.titlecase() }} at ${dateTime.format(
                DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH),
            )}"
        else -> {
            val day = dateTime.dayOfMonth
            val suffix =
                when {
                    day in 11..13 -> "th"
                    day % 10 == 1 -> "st"
                    day % 10 == 2 -> "nd"
                    day % 10 == 3 -> "rd"
                    else -> "th"
                }
            val monthYear = dateTime.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH))
            "$day$suffix $monthYear"
        }
    }
}

fun String.collapseWhileSpace(): String =
    this
        .replace('\u00A0', ' ')
        .replace(Regex("\\s+"), " ")
        .trim()

fun String.extractHtmlToPlain(): String = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

fun String.removeModPrefix(): String {
    val prefixes = listOf("mod_", "local_")

    val foundPrefix = prefixes.find { this.startsWith(it, ignoreCase = true) }

    val withoutPrefix =
        foundPrefix?.let { prefix ->
            this.drop(prefix.length)
        } ?: this

    return withoutPrefix.lowercase()
}

/** Build a JSONObject from data map and try to decode common JSON-string fields. */
fun Map<String, String>.toRichJson(): org.json.JSONObject {
    val root = org.json.JSONObject()
    // Add the raw data map
    val dataObj = org.json.JSONObject()
    for ((k, v) in this) {
        // If a value itself looks like JSON, store it as JSON; otherwise as string
        val parsed =
            try {
                when {
                    v.isJsonObject() -> org.json.JSONObject(v)
                    v.isJsonArray() -> org.json.JSONArray(v)
                    else -> v
                }
            } catch (_: Throwable) {
                v
            }
        dataObj.put(k, parsed)
    }
    root.put("data", dataObj)

    // Convenience: lift and pretty common fields
    fun putIfPresentPretty(key: String) {
        this[key]?.let { raw -> root.put(key, prettyJsonOrRaw(raw)) }
    }
    putIfPresentPretty("extra")
    putIfPresentPretty("data")
    putIfPresentPretty("customdata")

    return root
}

fun Window.applySecureFlagIfNeeded(isProduction: Boolean) {
    if (isProduction) {
        setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE,
        )
    } else {
        clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }
}

fun Context.findActivity(): Activity? =
    when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

fun isValidEmail(email: String): Boolean =
    android.util.Patterns.EMAIL_ADDRESS
        .matcher(email)
        .matches()
