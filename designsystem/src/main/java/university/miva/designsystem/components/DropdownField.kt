package university.miva.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDropdownField(
    modifier: Modifier = Modifier,
    label: String? = null,
    value: String,
    placeholder: String,
    items: List<String>,
    onItemSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Column {
            if (label.isNullOrEmpty().not()) {
                Text(
                    text = label,
                    style =
                        Theme.typography.h1.copy(
                            color = MivaColors.Secondary.BlueDeep,
                            fontSize = 14.sp,
                        ),
                )
                Spacer(modifier = Modifier.height(Theme.spacing.x2))
            }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = {},
                    readOnly = true,
                    textStyle =
                        Theme.typography.h1.copy(
                            color = MivaColors.Secondary.BlueDeep,
                            fontSize = 14.sp,
                        ),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .menuAnchor(
                                type = ExposedDropdownMenuAnchorType.PrimaryEditable,
                                enabled = true,
                            ),
                    placeholder = { Text(placeholder) },
                    colors =
                        ExposedDropdownMenuDefaults.outlinedTextFieldColors().copy(
                            focusedIndicatorColor = MivaColors.Secondary.Gray300,
                            unfocusedIndicatorColor = MivaColors.Secondary.Gray300,
                            unfocusedTextColor = MivaColors.Secondary.BlueDeep,
                            focusedTextColor = MivaColors.Secondary.BlueDeep,
                            focusedPlaceholderColor = MivaColors.Secondary.HintColor,
                            unfocusedPlaceholderColor = MivaColors.Secondary.HintColor,
                            focusedContainerColor = MivaColors.White,
                            unfocusedContainerColor = MivaColors.White,
                        ),
                    shape = RoundedCornerShape(Theme.spacing.x2),
                    trailingIcon = {
                        if (expanded) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = stringResource(R.string.dropdown_icon),
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = stringResource(R.string.dropdown_icon),
                            )
                        }
                    },
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    containerColor = MivaColors.White,
                    onDismissRequest = { expanded = false },
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    item,
                                    style =
                                        Theme.typography.bodyM.copy(
                                            color = MivaColors.Secondary.BlueDeep,
                                            fontSize = 16.sp,
                                        ),
                                )
                            },
                            onClick = {
                                onItemSelected(item)
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
    }
}
