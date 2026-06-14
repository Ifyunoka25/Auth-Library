package university.miva.designsystem.components.field

import Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.datePickerColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolderText: String = "",
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val selectionColors =
        TextSelectionColors(
            handleColor = MivaColors.Secondary.BlueDeep,
            backgroundColor = MivaColors.Secondary.BlueDeep.copy(alpha = 0.4f),
        )

    val color =
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
            errorTextColor = MivaColors.TextColors.Red500,
            cursorColor = MivaColors.Secondary.BlueDeep,
            textSelectionColors = selectionColors,
        )

    Column(modifier = modifier) {
        CompositionLocalProvider(
            LocalTextSelectionColors provides selectionColors,
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = {
                    Text(
                        text = placeHolderText,
                        style =
                            Theme.typography.bodyM.copy(
                                color = MivaColors.Secondary.GrayPrimary10,
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                            ),
                    )
                },
                textStyle =
                    Theme.typography.bodyM.copy(
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                    ),
                modifier = Modifier.fillMaxWidth(),
                readOnly = readOnly,
                isError = isError,
                keyboardOptions = keyboardOptions,
                trailingIcon = trailingIcon,
                colors = color,
                shape = RoundedCornerShape(Theme.spacing.x2),
            )
        }
        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                style =
                    Theme.typography.bodyM.copy(
                        color = MivaColors.TextColors.Red500,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                    ),
                modifier = Modifier.padding(start = Theme.spacing.x2, top = Theme.spacing.x1),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDropdownField(
    value: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    enabled: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (enabled) expanded = it },
        modifier = modifier,
    ) {
        Box(
            modifier =
                Modifier
                    .menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryEditable,
                        enabled = true,
                    ).fillMaxWidth()
                    .clip(RoundedCornerShape(Theme.spacing.x2))
                    .border(
                        width = 1.dp,
                        color = if (expanded) MivaColors.Secondary.BlueDeep else MivaColors.Secondary.NeutralSeven,
                        shape = RoundedCornerShape(Theme.spacing.x2),
                    ).background(MivaColors.White)
                    .clickable(enabled = enabled) { expanded = true }
                    .padding(
                        horizontal = Theme.spacing.x3,
                        vertical = Theme.spacing.x3,
                    ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = value.ifEmpty { placeholder },
                    style =
                        Theme.typography.bodyS.copy(
                            color =
                                if (value.isEmpty()) {
                                    MivaColors.Secondary.NeutralFive
                                } else {
                                    MivaColors.Secondary.BlueDeep
                                },
                        ),
                    modifier = Modifier.weight(1f),
                )
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier =
                Modifier
                    .background(MivaColors.White)
                    .heightIn(max = 250.dp),
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            style =
                                Theme.typography.bodyS.copy(
                                    color = MivaColors.Secondary.BlueDeep,
                                ),
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerField(
    value: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Select date",
    enabled: Boolean = true,
    minAge: Int = 16, // Minimum age required
    maxAge: Int = 100, // Maximum age allowed
) {
    var showDatePicker by remember { mutableStateOf(false) }

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    // Calculate date range based on age
    val maxDateMillis =
        Calendar
            .getInstance()
            .apply {
                add(Calendar.YEAR, -minAge) // e.g., 16 years ago (minimum age)
            }.timeInMillis

    val minDateMillis =
        Calendar
            .getInstance()
            .apply {
                add(Calendar.YEAR, -maxAge) // e.g., 100 years ago (maximum age)
            }.timeInMillis

    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = maxDateMillis,
            yearRange = (currentYear - maxAge)..(currentYear - minAge),
            selectableDates =
                object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                        utcTimeMillis in minDateMillis..maxDateMillis

                    override fun isSelectableYear(year: Int): Boolean =
                        year in (currentYear - maxAge)..(currentYear - minAge)
                },
        )

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.spacing.x2))
                .border(
                    width = 1.dp,
                    color = MivaColors.Secondary.NeutralSeven,
                    shape = RoundedCornerShape(Theme.spacing.x2),
                ).background(MivaColors.White)
                .clickable(enabled = enabled) { showDatePicker = true }
                .padding(
                    horizontal = Theme.spacing.x3,
                    vertical = Theme.spacing.x3,
                ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_quiz_time),
                contentDescription = null,
                tint = MivaColors.Secondary.BlueDeep,
            )
            Spacer(modifier = Modifier.width(Theme.spacing.x2))
            Text(
                text = value.ifEmpty { placeholder },
                style =
                    Theme.typography.bodyS.copy(
                        color =
                            if (value.isEmpty()) {
                                MivaColors.Secondary.NeutralFive
                            } else {
                                MivaColors.Secondary.BlueDeep
                            },
                    ),
                modifier =
                    Modifier.weight(1f).padding(
                        vertical = Theme.spacing.x1,
                    ),
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            colors =
                DatePickerDefaults.colors().copy(
                    containerColor = MivaColors.White,
                ),
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val dateFormat = SimpleDateFormat("EEE, dd MMM, yyyy", Locale.getDefault())
                            val formattedDate = dateFormat.format(Date(millis))
                            onDateSelected(formattedDate)
                        }
                        showDatePicker = false
                    },
                ) {
                    Text(
                        text = stringResource(R.string.confirm),
                        style =
                            Theme.typography.bodyS.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style =
                            Theme.typography.headingM.copy(
                                color = MivaColors.Secondary.BlueDeep,
                                fontSize = 14.sp,
                            ),
                    )
                }
            },
        ) {
            DatePicker(
                state = datePickerState,
                colors = datePickerColor(),
            )
        }
    }
}
