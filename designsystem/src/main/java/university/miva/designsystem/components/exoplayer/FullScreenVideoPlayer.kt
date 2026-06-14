package university.miva.designsystem.components.exoplayer

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import university.miva.designsystem.components.CustomizeSystemBars

@Composable
fun FullScreenVideoPlayer(
    navController: NavController,
    videoUrl: String,
    videoTitle: String,
) {
    val context = LocalContext.current as Activity
    val exoPlayer = ReusableVideoPlayerController.getPlayer(context, videoUrl)

    LaunchedEffect(exoPlayer) {
        ReusableVideoPlayerController.restoreState()
    }

    DisposableEffect(Unit) {
        val originalOrientation = context.requestedOrientation
        context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        val window = context.window
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        onDispose {
            ReusableVideoPlayerController.saveState()

            // Restore original settings
            context.requestedOrientation = originalOrientation
            insetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    CustomizeSystemBars(
        darkIcons = true,
    )

    ExoVideoPlayer(
        modifier = Modifier.fillMaxSize(),
        videoTitle = videoTitle,
        exoPlayer = exoPlayer,
        isFullScreen = true,
        onFullScreenToggle = {
            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            ReusableVideoPlayerController.saveState()
            navController.popBackStack()
        },
    )
}
