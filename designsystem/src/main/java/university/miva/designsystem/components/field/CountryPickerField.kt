package university.miva.designsystem.components.field

import Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import university.miva.designsystem.R
import university.miva.designsystem.components.dialog.CountryPickerContent
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.countries.Country

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPickerField(
    selectedCountry: Country?,
    onCountrySelected: (Country) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Select Country",
    enabled: Boolean = true,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.spacing.x2))
                .border(
                    width = 1.dp,
                    color = MivaColors.Secondary.Gray300,
                    shape = RoundedCornerShape(Theme.spacing.x2),
                ).background(MivaColors.White)
                .clickable(enabled = enabled) { showBottomSheet = true }
                .padding(
                    horizontal = Theme.spacing.x4,
                    vertical = Theme.spacing.x4,
                ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (selectedCountry != null) {
                Text(
                    text = "${selectedCountry.flag} ${selectedCountry.name}",
                    style = Theme.typography.bodyM,
                    modifier = Modifier.weight(1f),
                )
            } else {
                Text(
                    text = placeholder,
                    style =
                        Theme.typography.bodyM.copy(
                            color = MivaColors.Secondary.HintColor,
                        ),
                    modifier = Modifier.weight(1f),
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = stringResource(R.string.dropdown_icon),
                tint = MivaColors.Secondary.BlueDeep,
            )
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MivaColors.White,
        ) {
            CountryPickerContent(
                onCountrySelected = { country ->
                    onCountrySelected(country)
                    showBottomSheet = false
                },
            )
        }
    }
}
