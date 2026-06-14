package university.miva.designsystem.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import university.miva.designsystem.theme.MivaColors

@Composable
fun AppShimmer(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        Box(modifier = modifier.shimmerEffect())
    } else {
        contentAfterLoading()
    }
}

fun Modifier.shimmerEffect(): Modifier =
    composed {
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }

        val transition = rememberInfiniteTransition(label = "shimmerTransition")
        val startOffsetX by transition.animateFloat(
            initialValue = -2 * size.width.toFloat(),
            targetValue = 2 * size.width.toFloat(),
            animationSpec =
                infiniteRepeatable(
                    animation = tween(2000),
                ),
            label = "shimmerAnimation",
        )

        background(
            brush =
                Brush.linearGradient(
                    colors =
                        listOf(
                            MivaColors.Secondary.Shimmer100,
                            MivaColors.Secondary.Shimmer200,
                            MivaColors.Secondary.Shimmer300,
                        ),
                    start = Offset(startOffsetX, 0f),
                    end =
                        Offset(
                            startOffsetX + size.width.toFloat(),
                            size.height.toFloat(),
                        ),
                ),
        ).onGloballyPositioned {
            size = it.size
        }
    }
