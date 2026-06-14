package university.miva.designsystem.components.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.countries.Countries
import university.miva.designsystem.util.countries.Country

@Composable
fun CountryPickerContent(onCountrySelected: (Country) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredCountries =
        remember(searchQuery) {
            if (searchQuery.isBlank()) {
                // Show popular countries first, then the rest (excluding duplicates)
                val popularCodes = Countries.popularCountries.map { it.code }.toSet()
                val otherCountries = Countries.all.filter { it.code !in popularCodes }
                Countries.popularCountries + otherCountries
            } else {
                Countries.search(searchQuery)
            }
        }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.spacing.x4),
    ) {
        Text(
            text = stringResource(R.string.select_country),
            style =
                Theme.typography.h4.copy(
                    fontWeight = FontWeight.Bold,
                    color = MivaColors.Secondary.BlueDeep,
                ),
        )

        Spacer(modifier = Modifier.height(Theme.spacing.x4))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    text = stringResource(R.string.search_country),
                    style =
                        Theme.typography.bodyM.copy(
                            color = MivaColors.Secondary.HintColor,
                        ),
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_country),
                    tint = MivaColors.Secondary.BlueDeep,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Theme.spacing.x2),
            singleLine = true,
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MivaColors.Secondary.BlueDeep,
                    unfocusedBorderColor = MivaColors.Secondary.Gray300,
                    focusedContainerColor = MivaColors.White,
                    unfocusedContainerColor = MivaColors.White,
                ),
        )

        Spacer(modifier = Modifier.height(Theme.spacing.x4))
        if (searchQuery.isNotBlank() && filteredCountries.isEmpty()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = Theme.spacing.x8),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.no_country_found),
                    style = Theme.typography.bodyM,
                )
                Spacer(modifier = Modifier.height(Theme.spacing.x2))
                Text(
                    text = stringResource(R.string.try_different_search),
                    style = Theme.typography.bodyS,
                )
            }
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp),
            ) {
                items(filteredCountries) { country ->
                    CountryItem(
                        country = country,
                        onClick = { onCountrySelected(country) },
                    )
                    HorizontalDivider(
                        color = MivaColors.Secondary.Gray200,
                        thickness = 0.5.dp,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Theme.spacing.x6))
    }
}

@Composable
private fun CountryItem(
    country: Country,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = Theme.spacing.x3),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = country.flag,
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.width(Theme.spacing.x3))
        Text(
            text = country.name,
            style =
                Theme.typography.bodyM.copy(
                    color = MivaColors.Secondary.BlueDeep,
                ),
            modifier = Modifier.weight(1f),
        )
        Text(
            text = country.phoneCode,
            style =
                Theme.typography.bodyS.copy(
                    color = MivaColors.Secondary.NeutralFive,
                ),
        )
    }
}
