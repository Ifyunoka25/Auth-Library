package university.miva.designsystem.components.dialog.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

enum class RepeatInterval(
    val title: String,
    val id: String,
) {
    EVERY_DAY("Every day", "daily"),
    EVERY_WEEK("Every week", "weekly"),
    EVERY_TWO_WEEKS("Every 2 weeks", "weekly"),
    EVERY_MONTH("Every month", "monthly"),
    EVERY_YEAR("Every year", "yearly"),
    NONE("", ""),
    ;

    companion object {
        fun fromTitle(title: String): RepeatInterval =
            entries.firstOrNull { it.title.equals(title, ignoreCase = true) } ?: NONE

        fun fromId(id: String): RepeatInterval = entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: NONE
    }
}

fun calculateRecurrenceCount(
    startDate: String,
    endDate: String,
    interval: RepeatInterval,
): Int {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val start = LocalDate.parse(startDate, formatter)
    val end = LocalDate.parse(endDate, formatter)

    if (interval == RepeatInterval.NONE) return 1
    if (end.isBefore(start)) return 0

    return when (interval) {
        RepeatInterval.EVERY_DAY -> ChronoUnit.DAYS.between(start, end).toInt() + 1
        RepeatInterval.EVERY_WEEK -> ChronoUnit.WEEKS.between(start, end).toInt() + 1
        RepeatInterval.EVERY_TWO_WEEKS -> (ChronoUnit.WEEKS.between(start, end) / 2).toInt() + 1
        RepeatInterval.EVERY_MONTH -> ChronoUnit.MONTHS.between(start, end).toInt() + 1
        RepeatInterval.EVERY_YEAR -> ChronoUnit.YEARS.between(start, end).toInt() + 1
        RepeatInterval.NONE -> 1
    }
}
