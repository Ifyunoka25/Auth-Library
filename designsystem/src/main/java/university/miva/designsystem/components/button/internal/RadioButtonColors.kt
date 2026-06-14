package university.miva.designsystem.components.button.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

internal data class RadioButtonColors(
    val activeBorderColor: Color = Color.Unspecified,
    val inactiveBorderColor: Color = Color.Unspecified,
)

internal val LocalRadioButtonColorProvider = compositionLocalOf { RadioButtonColors() }

@Composable
internal fun ProvideRadioButtonColors(
    colors: RadioButtonColors,
    content: @Composable () -> Unit,
) = CompositionLocalProvider(LocalRadioButtonColorProvider provides colors, content = content)
