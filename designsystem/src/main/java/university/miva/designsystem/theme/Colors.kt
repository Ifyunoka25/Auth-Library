package university.miva.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class Colors(
    val surface: Surface,
    val surfaceInteractive: SurfaceInteractive,
    val shadow: Shadow,
    val border: Border,
    val element: Element,
    val elementInteractive: ElementInteractive,
)

@Immutable
data class Surface(
    val primaryLight: Color,
    val secondaryLight: Color,
    val primaryDark: Color,
    val secondaryDark: Color,
)

@Immutable
data class SurfaceInteractive(
    val enabledOnLight: Color,
    val hoverTapOnLight: Color,
    val disabledOnLight: Color,
    val enabledOnDark: Color,
    val hoverTapOnDark: Color,
    val disabledOnDark: Color,
)

@Immutable
data class Border(
    val primaryOnLight: Color,
    val secondaryOnLight: Color,
    val primaryOnDark: Color,
    val secondaryOnDark: Color,
)

@Immutable
data class Shadow(
    val spotOnLight: Color,
    val spotOnDark: Color,
)

@Immutable
data class Element(
    val primaryOnLight: Color,
    val secondaryOnLight: Color,
    val primaryOnDark: Color,
    val secondaryOnDark: Color,
)

@Immutable
data class ElementInteractive(
    val enabledOnLight: Color,
    val hoverTapOnLight: Color,
    val disabledOnLight: Color,
    val enabledOnDark: Color,
    val hoverTapOnDark: Color,
    val disabledOnDark: Color,
)
