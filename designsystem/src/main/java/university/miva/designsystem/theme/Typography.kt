package university.miva.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle

@Immutable
data class Typography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val headingS: TextStyle,
    val headingM: TextStyle,
    val body: TextStyle,
    val bodyM: TextStyle,
    val bodyS: TextStyle,
    val bodyXs: TextStyle,
    val labelS: TextStyle,
    val labelXs: TextStyle,
    val chips: TextStyle,
)
