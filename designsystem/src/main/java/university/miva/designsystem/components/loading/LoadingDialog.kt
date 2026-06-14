package university.miva.designsystem.components.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors

@Composable
fun LoadingDialog() {
    Dialog(onDismissRequest = {}) {
        Box(
            modifier =
                Modifier
                    .height(120.dp)
                    .width(110.dp)
                    .background(MivaColors.Secondary.Transparent),
            contentAlignment = Alignment.Center,
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader_position))

            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                renderMode = RenderMode.SOFTWARE,
            )
        }
    }
}

@Composable
fun PaymentLoadingDialog() {
    Dialog(onDismissRequest = {}) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader_position))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LottieAnimation(
                    modifier =
                        Modifier
                            .height(120.dp)
                            .width(110.dp)
                            .background(MivaColors.Secondary.Transparent),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    renderMode = RenderMode.SOFTWARE,
                )

                Text(
                    text = stringResource(R.string.payment_processing),
                    modifier =
                        Modifier.padding(
                            vertical = Theme.spacing.x1,
                        ),
                    style =
                        Theme.typography.bodyM.copy(
                            fontSize = 16.sp,
                            color = MivaColors.White,
                            textAlign = TextAlign.Center,
                        ),
                )
            }
        }
    }
}
