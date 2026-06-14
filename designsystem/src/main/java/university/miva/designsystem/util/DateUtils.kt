package university.miva.designsystem.util

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import university.miva.designsystem.theme.MivaColors
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

// Date Format Sun, 25th 2026
fun getFormattedDate(): String {
    val calendar = Calendar.getInstance()

    val dayOfWeekFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
    val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())

    val dayOfWeek = dayOfWeekFormat.format(calendar.time)
    val day = getDayWithSuffix(calendar.get(Calendar.DAY_OF_MONTH))
    val month = monthFormat.format(calendar.time)
    val year = yearFormat.format(calendar.time)

    return "$dayOfWeek, $day $month $year"
}

fun getDayWithSuffix(day: Int): String =
    when {
        day in 11..13 -> "${day}th"
        day % 10 == 1 -> "${day}st"
        day % 10 == 2 -> "${day}nd"
        day % 10 == 3 -> "${day}rd"
        else -> "${day}th"
    }

fun getFormattedDateFromMillis(dateMillis: Long): String {
    val date =
        Instant
            .ofEpochMilli(dateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

    val day = date.dayOfMonth
    val dayWithSuffix =
        when {
            day in 11..13 -> "${day}th"
            day % 10 == 1 -> "${day}st"
            day % 10 == 2 -> "${day}nd"
            day % 10 == 3 -> "${day}rd"
            else -> "${day}th"
        }

    val dayOfWeek = date.format(DateTimeFormatter.ofPattern("EEE", Locale.getDefault()))
    val month = date.format(DateTimeFormatter.ofPattern("MMM", Locale.getDefault()))
    val year = date.year

    return "$dayOfWeek, $dayWithSuffix $month $year"
}

fun Int.toOrdinal(): String {
    if (this in 11..13) return "${this}th"
    return when (this % 10) {
        1 -> "${this}st"
        2 -> "${this}nd"
        3 -> "${this}rd"
        else -> "${this}th"
    }
}

// 2025-12-08T00:00:00Z to Mon, 24th Aug 2025 format
fun formatServerDate(date: String): String {
    val instant = Instant.parse(date)

    val zoned = instant.atZone(ZoneId.systemDefault())

    val dayName = zoned.format(DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH))
    val month = zoned.format(DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH))
    val year = zoned.year
    val day = zoned.dayOfMonth.toOrdinal()

    return "$dayName, $day $month $year"
}

// Mon, 24th Aug 2025  to 2025-12-08T00:00:00Z format
fun humanDateToServer(date: String): String {
    val cleaned = date.replace(Regex("(\\d+)(st|nd|rd|th)"), "$1")

    val formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH)
    val localDate = LocalDate.parse(cleaned, formatter)

    return localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toString()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appTextFieldColors() =
    ExposedDropdownMenuDefaults.outlinedTextFieldColors().copy(
        focusedIndicatorColor = MivaColors.Secondary.Gray300,
        unfocusedIndicatorColor = MivaColors.Secondary.Gray300,
        unfocusedTextColor = MivaColors.Secondary.BlueDeep,
        focusedTextColor = MivaColors.Secondary.BlueDeep,
        focusedPlaceholderColor = MivaColors.Secondary.HintColor,
        unfocusedPlaceholderColor = MivaColors.Secondary.HintColor,
        focusedContainerColor = MivaColors.White,
        unfocusedContainerColor = MivaColors.White,
        disabledContainerColor = MivaColors.Secondary.Gray300,
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun datePickerColor() =
    DatePickerDefaults.colors().copy(
        containerColor = MivaColors.White,
        titleContentColor = MivaColors.Secondary.BlueDeep,
        headlineContentColor = MivaColors.Secondary.BlueDeep,
        weekdayContentColor = MivaColors.Secondary.BlueDeep,
        subheadContentColor = MivaColors.Secondary.BlueDeep,
        yearContentColor = MivaColors.Secondary.BlueDeep,
        selectedDayContentColor = MivaColors.White,
        selectedDayContainerColor = MivaColors.Secondary.BlueDeep,
        dayContentColor = MivaColors.Secondary.BlueDeep,
        todayContentColor = MivaColors.Secondary.BlueDeep,
        selectedYearContainerColor = MivaColors.Secondary.BlueDeep,
        selectedYearContentColor = MivaColors.White,
        todayDateBorderColor = MivaColors.Secondary.BlueDeep,
        navigationContentColor = MivaColors.Secondary.BlueDeep,
        dateTextFieldColors =
            TextFieldDefaults.colors().copy(
                unfocusedIndicatorColor = MivaColors.Secondary.BlueDeep,
                focusedIndicatorColor = MivaColors.Secondary.BlueDeep,
                disabledIndicatorColor = MivaColors.Secondary.BlueDeep,
                unfocusedLabelColor = MivaColors.Secondary.BlueDeep,
                focusedLabelColor = MivaColors.Secondary.BlueDeep,
                disabledLabelColor = MivaColors.Secondary.BlueDeep,
                unfocusedContainerColor = MivaColors.White,
                focusedContainerColor = MivaColors.White,
                disabledContainerColor = MivaColors.White,
                errorContainerColor = MivaColors.White,
            ),
    )
