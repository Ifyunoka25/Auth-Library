package university.miva.designsystem.components.field

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.components.dialog.CountryPickerContent
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.countries.Country

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberField(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    selectedCountry: Country,
    onCountrySelected: (Country) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Enter phone number",
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String = "",
) {
    var showCountryPicker by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(Theme.spacing.x2))
                    .border(
                        width = 1.dp,
                        color = MivaColors.Secondary.Gray300,
                        shape = RoundedCornerShape(Theme.spacing.x2),
                    ).background(MivaColors.White)
                    .clickable(enabled = enabled) { showCountryPicker = true }
                    .padding(
                        horizontal = Theme.spacing.x3,
                        vertical = Theme.spacing.x4,
                    ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = selectedCountry.flag,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.width(Theme.spacing.x1))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.select_country),
                    tint = MivaColors.Secondary.BlueDeep,
                )
            }
        }

        Spacer(modifier = Modifier.width(Theme.spacing.x2))
        AppTextField(
            value = phoneNumber,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    onPhoneNumberChange(it)
                }
            },
            placeHolderText = placeholder,
            modifier = Modifier.weight(1f),
            isError = isError,
            errorMessage = errorMessage,
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                ),
        )
    }

    if (showCountryPicker) {
        ModalBottomSheet(
            onDismissRequest = { showCountryPicker = false },
            sheetState = sheetState,
            containerColor = MivaColors.White,
        ) {
            CountryPickerContent(
                onCountrySelected = { country ->
                    onCountrySelected(country)
                    showCountryPicker = false
                },
            )
        }
    }
}
