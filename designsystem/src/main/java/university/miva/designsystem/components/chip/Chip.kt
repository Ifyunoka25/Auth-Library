package university.miva.designsystem.components.chip

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    text: @Composable () -> Unit,
    backgroundColor: Color,
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(Theme.spacing.x2),
        modifier =
            Modifier
                .height(Theme.spacing.x6),
    ) {
        text()
    }
}

@Composable
fun RoundedChip(
    text: @Composable () -> Unit,
    backgroundColor: Color,
) {
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(50.dp),
        modifier =
            Modifier
                .height(Theme.spacing.x6),
    ) {
        text()
    }
}
