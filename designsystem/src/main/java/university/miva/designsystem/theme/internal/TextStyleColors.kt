package university.miva.designsystem.theme.internal

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class TextStyleColors(
    val regular: Color = Color.Unspecified,
    val caution: Color = Color.Unspecified,
    val success: Color = Color.Unspecified,
    val error: Color = Color.Unspecified,
)
