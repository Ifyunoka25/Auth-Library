package university.miva.designsystem.components.date

import Theme
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.datePickerColor
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    onDateSelected: (Long?) -> Unit,
    initialDate: Long = Calendar.getInstance().timeInMillis,
    selectableDates: SelectableDates = PastOrPresentSelectableDates,
    onDismiss: () -> Unit,
) {
    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = initialDate,
            selectableDates = selectableDates,
        )
    val formatter =
        DatePickerDefaults.dateFormatter(
            selectedDateSkeleton = "EEE, MMM d",
        )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors =
            DatePickerDefaults.colors().copy(
                containerColor = MivaColors.White,
            ),
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(
                    stringResource(R.string.ok),
                    style =
                        Theme.typography.bodyS.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(R.string.cancel),
                    style =
                        Theme.typography.headingM.copy(
                            color = MivaColors.Secondary.BlueDeep,
                            fontSize = 14.sp,
                        ),
                )
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = true),
    ) {
        DatePicker(
            state = datePickerState,
            dateFormatter = formatter,
            colors = datePickerColor(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
object PastOrPresentSelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val todayStartMillis =
            LocalDate
                .now()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

        return utcTimeMillis >= todayStartMillis
    }

    override fun isSelectableYear(year: Int): Boolean = year >= LocalDate.now().year
}

@OptIn(ExperimentalMaterial3Api::class)
fun endDateSelectableDates(startDateMillis: Long): SelectableDates {
    return object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean = utcTimeMillis >= startDateMillis

        override fun isSelectableYear(year: Int): Boolean {
            val startYear = LocalDate.ofEpochDay(startDateMillis / (1000 * 60 * 60 * 24)).year
            return year >= startYear
        }
    }
}
