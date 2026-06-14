package university.miva.designsystem.components.progress

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import university.miva.designsystem.theme.MivaColors

@Composable
fun AppLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MivaColors.Secondary.BlueDeep,
    trackColor: Color = MivaColors.Secondary.Gray300,
    height: Dp = 5.dp,
) {
    LinearProgressIndicator(
        progress = { progress },
        modifier =
            modifier
                .fillMaxWidth()
                .height(height),
        color = color,
        trackColor = trackColor,
        strokeCap = StrokeCap.Round,
        gapSize = (-15).dp,
        drawStopIndicator = {},
    )
}
