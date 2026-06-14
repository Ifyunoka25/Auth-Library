
import androidx.compose.runtime.Composable
import university.miva.designsystem.theme.Colors
import university.miva.designsystem.theme.LocalColorProvider
import university.miva.designsystem.theme.LocalShapeProvider
import university.miva.designsystem.theme.LocalSpacingProvider
import university.miva.designsystem.theme.LocalTypographyProvider
import university.miva.designsystem.theme.Shape
import university.miva.designsystem.theme.Spacing
import university.miva.designsystem.theme.Typography

object Theme {
    val colors: Colors @Composable get() = LocalColorProvider.current
    val spacing: Spacing @Composable get() = LocalSpacingProvider.current
    val typography: Typography @Composable get() = LocalTypographyProvider.current
    val shape: Shape @Composable get() = LocalShapeProvider.current
}
