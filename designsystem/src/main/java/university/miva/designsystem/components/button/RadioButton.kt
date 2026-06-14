package university.miva.designsystem.components.button

import Theme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import university.miva.designsystem.components.button.internal.ProvideRadioButtonColors
import university.miva.designsystem.components.button.internal.RadioButtonColors
import university.miva.designsystem.components.text.BasicText
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.theme.isSystemInDarkTheme

@Composable
fun RadioButton(
    checked: Boolean,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    width: Dp = 10.dp,
    disabledColor: Color = MivaColors.Secondary.Blue300,
    onClick: () -> Unit,
    invertedColors: Boolean = isSystemInDarkTheme(),
) {
    ProvideRadioButtonColors(colors = provideColors(invertedColors)) {
        Row(
            modifier = modifier.clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            androidx.compose.material3.RadioButton(
                selected = checked,
                onClick = {
                    onClick()
                },
                enabled = enabled,
                colors =
                    androidx.compose.material3.RadioButtonColors(
                        selectedColor = MivaColors.Primary.MainBlue,
                        unselectedColor = disabledColor,
                        disabledSelectedColor = MivaColors.Secondary.Gray600,
                        disabledUnselectedColor = disabledColor,
                    ),
            )
            if (text.isNotEmpty()) {
                Spacer(modifier = modifier.width(width))
                if (text.isNotEmpty()) {
                    BasicText(
                        text,
                        style =
                            Theme.typography.bodyS.copy(
                                color = MivaColors.Secondary.Blue400,
                                fontWeight = FontWeight.Medium,
                            ),
                    )
                }
            }
        }
    }
}

private fun provideColors(invertedColors: Boolean) =
    when (invertedColors) {
        true ->
            RadioButtonColors(
                activeBorderColor = MivaColors.Primary.MainBlue,
                inactiveBorderColor = MivaColors.Secondary.Blue300,
            )
        false ->
            RadioButtonColors(
                activeBorderColor = MivaColors.Primary.MainBlue,
                inactiveBorderColor = MivaColors.Secondary.Blue300,
            )
    }
