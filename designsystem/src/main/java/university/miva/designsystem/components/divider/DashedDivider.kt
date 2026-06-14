package university.miva.designsystem.components.divider

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import university.miva.designsystem.theme.MivaColors

@Composable
fun DashedDividerLine(modifier: Modifier = Modifier) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(modifier = modifier) {
        drawLine(
            color = MivaColors.Secondary.UnselectedCarouselColor,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect,
        )
    }
}
