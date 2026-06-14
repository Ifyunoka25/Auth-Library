@file:Suppress("DEPRECATION")

package university.miva.designsystem.components.youtubePlayer

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.View
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.Routes
import university.miva.designsystem.util.extractYouTubeId

@Composable
fun YouTubePlayerContainerScreen(
    videoUrl: String,
    navController: NavController,
) {
    val activity = LocalContext.current as? Activity
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    fun defaultUnspecifiedMode() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    fun defaultMode() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
    }

    LaunchedEffect(Unit) {
//        landscapeMode()
        activity?.window?.decorView?.systemUiVisibility =
            if (!isPortrait) {
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            } else {
                View.SYSTEM_UI_FLAG_VISIBLE
            }
    }

    BackHandler(onBack = {
        if (!isPortrait) {
            defaultUnspecifiedMode()
        } else {
            navController.navigate(Routes.GUEST)
        }
    })

    DisposableEffect(Unit) {
        onDispose {
            navController.navigate(Routes.GUEST)
            defaultMode()
        }
    }

    YouTubePlayerScreen(
        videoUrl = videoUrl,
        onFullscreenToggle = {
            if (isPortrait) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        },
        onBackPressed = {
            navController.navigate("guest")
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        },
    )
}

@Preview(
    name = "Landscape",
    group = "BoxWithConstraints",
    widthDp = 640,
    heightDp = 360,
)
@Preview(
    name = "Portrait",
    group = "BoxWithConstraints",
    widthDp = 360,
    heightDp = 640,
)
@Preview()
@Composable
fun YouTubePlayerScreenPreview() {
    YouTubePlayerScreen(
        videoUrl = "",
        onFullscreenToggle = {},
        onBackPressed = {},
    )
}

@Composable
fun YouTubePlayerScreen(
    videoUrl: String,
    onFullscreenToggle: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val videoId = remember { extractYouTubeId(videoUrl) }

    Box(modifier = Modifier.fillMaxSize().background(color = MivaColors.Primary.MainBlue)) {
        AndroidView(
            factory = { ctx ->
                YouTubePlayerView(ctx).apply {
                    enableAutomaticInitialization = false
                    initialize(
                        object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(videoId.orEmpty(), 0f)
                            }
                        },
                        IFramePlayerOptions
                            .Builder()
                            .controls(1)
                            .build(),
                    )
                }
            },
            modifier = Modifier.fillMaxSize(),
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_back_arrow),
            tint = MivaColors.White,
            contentDescription = stringResource(R.string.back_icon),
            modifier =
                Modifier
                    .align(if (isPortrait) Alignment.CenterStart else Alignment.TopStart)
                    .padding(
                        start = if (isPortrait) Theme.spacing.x4 else Theme.spacing.x6,
                        top = if (isPortrait) Theme.spacing.x3 else Theme.spacing.x4,
                        bottom = if (isPortrait) 160.dp else 0.dp,
                    ).clickable { onBackPressed() },
        )

        Icon(
            painter =
                painterResource(
                    id = if (isPortrait) R.drawable.ic_fullscreen else R.drawable.ic_exit_fullscreen,
                ),
            tint = MivaColors.White,
            contentDescription = "Fullscreen Toggle",
            modifier =
                Modifier
                    .align(if (isPortrait) Alignment.CenterEnd else Alignment.BottomEnd)
                    .padding(
                        top = if (isPortrait) 170.dp else 0.dp,
                        end = if (isPortrait) Theme.spacing.x6 else Theme.spacing.x5,
                        bottom = if (isPortrait) Theme.spacing.x6 else 30.dp,
                    ).size(Theme.spacing.x6)
                    .clickable { onFullscreenToggle() },
        )
    }
}
