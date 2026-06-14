package university.miva.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import university.miva.designsystem.theme.MivaColors

@Composable
fun AppSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    var isChecked by remember { mutableStateOf(checked) }
    val trackWidth = Theme.spacing.x10
    val trackHeight = 22.dp
    val thumbDiameter = 14.dp
    val padding = Theme.spacing.x1

    val thumbOffset by animateDpAsState(
        targetValue = if (checked) trackWidth - thumbDiameter - (padding * 2) else 0.dp,
        animationSpec = tween(durationMillis = 400), // Adjust duration as needed
    )

    val trackBackgroundColor by animateColorAsState(
        targetValue =
            if (checked) {
                MivaColors.Secondary.BlueDeep
            } else {
                MivaColors.Secondary.UnselectedCarouselColor
            },
        animationSpec = tween(durationMillis = 400),
    )

    Box(
        modifier =
            Modifier
                .clip(shape = CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication =
                        ripple(
                            color = MivaColors.Secondary.Blue200,
                        ),
                ) {
                    isChecked = !isChecked
                    onCheckedChange.invoke(isChecked)
                }.padding(Theme.spacing.x3),
    ) {
        Box(
            modifier =
                Modifier
                    .width(trackWidth)
                    .height(trackHeight)
                    .border(1.dp, MivaColors.Secondary.BlueDeep, RoundedCornerShape(Theme.spacing.x4))
                    .background(
                        trackBackgroundColor,
                        RoundedCornerShape(Theme.spacing.x4),
                    ),
        ) {
            Box(
                modifier =
                    Modifier
                        .padding(padding)
                        .size(thumbDiameter)
                        .offset(x = thumbOffset)
                        .background(MivaColors.White, CircleShape)
                        .border(1.dp, MivaColors.Secondary.BlueDeep, CircleShape),
            )
        }
    }
}
