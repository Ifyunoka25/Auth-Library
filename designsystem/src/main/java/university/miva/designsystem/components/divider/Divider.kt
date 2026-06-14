package university.miva.designsystem.components.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import university.miva.designsystem.theme.isSystemInDarkTheme

@Composable
fun Divider(
    modifier: Modifier = Modifier,
    color: Color? = null,
    thickness: Dp = 1.dp,
    invertedColors: Boolean = isSystemInDarkTheme(),
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color ?: componentColors(invertedColors),
    )
}

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color? = null,
    thickness: Dp = Theme.spacing.x1,
    invertedColors: Boolean = isSystemInDarkTheme(),
) {
    VerticalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color ?: componentColors(invertedColors),
    )
}

@Composable
private fun componentColors(invertedColors: Boolean) =
    when (invertedColors) {
        true -> Theme.colors.surfaceInteractive.enabledOnDark
        false -> Theme.colors.surfaceInteractive.enabledOnLight
    }
