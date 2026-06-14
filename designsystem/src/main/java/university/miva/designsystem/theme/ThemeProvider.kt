package university.miva.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import university.miva.designsystem.theme.internal.TextStyleColors

internal val LocalColorProvider =
    staticCompositionLocalOf<Colors> {
        Colors(
            surface =
                Surface(
                    primaryDark = Color.Unspecified,
                    primaryLight = Color.Unspecified,
                    secondaryDark = Color.Unspecified,
                    secondaryLight = Color.Unspecified,
                ),
            surfaceInteractive =
                SurfaceInteractive(
                    enabledOnDark = Color.Unspecified,
                    enabledOnLight = Color.Unspecified,
                    disabledOnDark = Color.Unspecified,
                    disabledOnLight = Color.Unspecified,
                    hoverTapOnDark = Color.Unspecified,
                    hoverTapOnLight = Color.Unspecified,
                ),
            border =
                Border(
                    primaryOnLight = Color.Unspecified,
                    primaryOnDark = Color.Unspecified,
                    secondaryOnDark = Color.Unspecified,
                    secondaryOnLight = Color.Unspecified,
                ),
            element =
                Element(
                    primaryOnLight = Color.Unspecified,
                    primaryOnDark = Color.Unspecified,
                    secondaryOnDark = Color.Unspecified,
                    secondaryOnLight = Color.Unspecified,
                ),
            shadow =
                Shadow(
                    spotOnDark = Color.Unspecified,
                    spotOnLight = Color.Unspecified,
                ),
            elementInteractive =
                ElementInteractive(
                    enabledOnDark = Color.Unspecified,
                    enabledOnLight = Color.Unspecified,
                    disabledOnDark = Color.Unspecified,
                    disabledOnLight = Color.Unspecified,
                    hoverTapOnDark = Color.Unspecified,
                    hoverTapOnLight = Color.Unspecified,
                ),
        )
    }

internal val LocalTypographyProvider =
    staticCompositionLocalOf {
        Typography(
            h1 = TextStyle.Default,
            h2 = TextStyle.Default,
            h3 = TextStyle.Default,
            h4 = TextStyle.Default,
            headingS = TextStyle.Default,
            headingM = TextStyle.Default,
            body = TextStyle.Default,
            bodyM = TextStyle.Default,
            bodyS = TextStyle.Default,
            bodyXs = TextStyle.Default,
            labelS = TextStyle.Default,
            labelXs = TextStyle.Default,
            chips = TextStyle.Default,
        )
    }

internal val LocalSpacingProvider =
    staticCompositionLocalOf {
        Spacing(
            x1 = 4.dp,
            x2 = 8.dp,
            x3 = 12.dp,
            x4 = 16.dp,
            x5 = 20.dp,
            x6 = 24.dp,
            x7 = 28.dp,
            x8 = 32.dp,
            x9 = 36.dp,
            x10 = 40.dp,
            x12 = 48.dp,
            x14 = 56.dp,
            x16 = 64.dp,
            x18 = 72.dp,
            x20 = 80.dp,
            x22 = 88.dp,
            x24 = 96.dp,
            x32 = 128.dp,
        )
    }

internal val LocalShapeProvider =
    staticCompositionLocalOf<Shape> {
        throw IllegalStateException("Shape has not been set")
    }

@Composable
fun WithTheme(
    colors: Colors,
    typography: Typography,
    clauseStyleColors: TextStyleColors,
    shape: Shape,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColorProvider provides colors,
        LocalTypographyProvider provides typography,
        LocalShapeProvider provides shape,
        content = content,
    )
}
