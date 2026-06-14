package university.miva.designsystem.components.date

import Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimePicker(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = LocalTime.now()

    val timePickerState =
        rememberTimePickerState(
            initialHour = currentTime.hour,
            initialMinute = currentTime.minute,
            is24Hour = false,
        )

    TimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState) },
    ) {
        TimePicker(
            state = timePickerState,
            colors =
                TimePickerDefaults.colors().copy(
                    selectorColor = MivaColors.Secondary.BlueDeep,
                    clockDialColor = MivaColors.Secondary.Grey50,
                    containerColor = MivaColors.White,
                    timeSelectorSelectedContainerColor = MivaColors.Secondary.Brown100,
                    timeSelectorUnselectedContentColor = MivaColors.Secondary.BlueDeep,
                    timeSelectorSelectedContentColor = MivaColors.Secondary.BlueDeep,
                    timeSelectorUnselectedContainerColor = MivaColors.Secondary.Grey50,
                    periodSelectorSelectedContentColor = MivaColors.Secondary.BlueDeep,
                    periodSelectorUnselectedContentColor = MivaColors.Secondary.BlueDeep,
                    periodSelectorSelectedContainerColor = MivaColors.Secondary.Brown100,
                    periodSelectorUnselectedContainerColor = MivaColors.Secondary.Grey50,
                ),
        )
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val dialogWidth = screenWidth * 0.9f

    AlertDialog(
        modifier = Modifier.width(dialogWidth),
        onDismissRequest = onDismiss,
        containerColor = MivaColors.White,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
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
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(
                    stringResource(R.string.ok),
                    style =
                        Theme.typography.headingM.copy(
                            color = MivaColors.Secondary.BlueDeep,
                            fontSize = 14.sp,
                        ),
                )
            }
        },
        text = {
            Column {
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = Theme.spacing.x5),
                    text = stringResource(R.string.select_time),
                    style =
                        Theme.typography.bodyS.copy(
                            color = MivaColors.Secondary.BlueDeep,
                        ),
                )
                content()
            }
        },
    )
}
