package university.miva.designsystem.components.button.internal

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

internal data class ButtonColors(
    val backgroundColor: Color = Color.Unspecified,
    val disabledBackgroundColor: Color = Color.Unspecified,
    val textColor: Color = Color.Unspecified,
    val disabledTextColor: Color = Color.Unspecified,
    val contentColor: Color = Color.Unspecified,
    val disabledContentColor: Color = Color.Unspecified,
    val borderOutlineColor: Color = Color.Unspecified,
    val disabledBorderOutlineColor: Color = Color.Unspecified,
)

internal val LocalButtonColorProvider = compositionLocalOf { ButtonColors() }

@Composable
internal fun ProvideButtonColors(
    colors: ButtonColors,
    content: @Composable () -> Unit,
) = CompositionLocalProvider(LocalButtonColorProvider provides colors, content = content)

@Composable
internal fun ButtonColors.toButtonColors() =
    ButtonDefaults.buttonColors(
        containerColor = backgroundColor,
        disabledContainerColor = disabledBackgroundColor,
    )
