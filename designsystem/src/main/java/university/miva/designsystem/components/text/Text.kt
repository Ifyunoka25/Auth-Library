package university.miva.designsystem.components.text

import Theme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun BasicText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = Theme.typography.body,
    maxLines: Int = Int.MAX_VALUE,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
        lineHeight = lineHeight,
        overflow = overflow,
        maxLines = maxLines ?: Int.MAX_VALUE,
    )
}
