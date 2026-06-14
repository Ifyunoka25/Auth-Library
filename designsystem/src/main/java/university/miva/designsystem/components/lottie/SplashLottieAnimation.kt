package university.miva.designsystem.components.lottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import university.miva.designsystem.R

@Composable
fun LottieAnimationScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.miva_splash_logo))

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        renderMode = RenderMode.SOFTWARE,
    )
}
