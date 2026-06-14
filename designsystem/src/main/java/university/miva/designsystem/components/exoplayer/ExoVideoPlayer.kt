package university.miva.designsystem.components.exoplayer

import Theme
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.PopupProperties
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.C
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import university.miva.designsystem.R
import university.miva.designsystem.analytics.DesignSystemAnalyticsParams.PLAYBACK_STARTED
import university.miva.designsystem.analytics.DesignSystemAnalyticsParams.PROGRESS_STATUS
import university.miva.designsystem.analytics.DesignSystemAnalyticsParams.VIDEO
import university.miva.designsystem.analytics.DesignSystemAnalyticsProvider
import university.miva.designsystem.analytics.DesignSystemVideoEvents.LESSON_COMPLETED
import university.miva.designsystem.analytics.DesignSystemVideoEvents.LESSON_PROGRESSED
import university.miva.designsystem.analytics.DesignSystemVideoEvents.LESSON_STARTED
import university.miva.designsystem.components.image.ClickableSpanImage
import university.miva.designsystem.theme.MivaColors

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ExoVideoPlayer(
    modifier: Modifier = Modifier,
    videoTitle: String?,
    exoPlayer: ExoPlayer,
    isFullScreen: Boolean,
    onFullScreenToggle: () -> Unit,
) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    var currentPosition by remember { mutableLongStateOf(0L) }
    var playbackState by remember { mutableIntStateOf(exoPlayer.playbackState) }
    var totalDuration by remember { mutableLongStateOf(0L) }
    val skipMs = 10_000L // 10 seconds

    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
    var showControls by remember { mutableStateOf(true) }
    var isUserScrubbing by remember { mutableStateOf(false) }

    val progressStatuses = listOf(0.25f, 0.5f, 0.75f, 0.95f)
    val loggedProgress by remember { mutableStateOf(mutableSetOf<Float>()) }
    var hasStarted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context.findActivity()
    val speedOptions = listOf(0.5f, 0.75f, 1.0f, 1.5f, 2.0f)
    var speedIndex by remember { mutableIntStateOf(speedOptions.indexOf(1.0f).takeIf { it >= 0 } ?: 2) }
    var showSpeedMenu by remember { mutableStateOf(false) }
    var firstFrameRendered by remember { mutableStateOf(false) }

    fun applySpeed(index: Int) {
        speedIndex = index
        exoPlayer.playbackParameters = PlaybackParameters(speedOptions[index])
        showControls = true
    }

    val listener =
        remember {
            object : Player.Listener {
                override fun onIsPlayingChanged(playing: Boolean) {
                    isPlaying = playing
                    activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON.takeIf { playing } ?: 0)
                    if (!playing) {
                        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    }
                }

                override fun onPlaybackStateChanged(state: Int) {
                    playbackState = state
                    if (state == Player.STATE_IDLE || state == Player.STATE_BUFFERING) {
                        firstFrameRendered = false
                    }
                }

                override fun onRenderedFirstFrame() {
                    firstFrameRendered = true
                }

                override fun onTimelineChanged(
                    timeline: Timeline,
                    reason: Int,
                ) {
                    totalDuration = exoPlayer.duration.coerceAtLeast(0L)
                }
            }
        }

    // show while loading/buffering or until first frame
    val showSpinner =
        playbackState == Player.STATE_IDLE ||
            playbackState == Player.STATE_BUFFERING ||
            (playbackState == Player.STATE_READY && !firstFrameRendered)

    // Initialise from player (in case something set it earlier)
    LaunchedEffect(exoPlayer) {
        exoPlayer.addListener(listener)
        val current = exoPlayer.playbackParameters.speed
        val idx = speedOptions.indexOfFirst { kotlin.math.abs(it - current) < 0.01f }
        speedIndex = if (idx >= 0) idx else speedOptions.indexOf(1.0f).coerceAtLeast(0)
    }

    // Hides controls after 3 seconds
    LaunchedEffect(showControls, playbackState, showSpeedMenu, isUserScrubbing) {
        if (showControls &&
            !showSpeedMenu &&
            !isUserScrubbing &&
            playbackState != Player.STATE_BUFFERING
        ) {
            delay(2000)
            showControls = false
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            // Check the state on every loop iteration
            if (isPlaying && !isUserScrubbing) {
                currentPosition = exoPlayer.currentPosition
                if (totalDuration > 0) {
                    sliderPosition = currentPosition.toFloat() / totalDuration

                    // Track video started
                    if (!hasStarted && sliderPosition > 0f) {
                        DesignSystemAnalyticsProvider.track(LESSON_STARTED, mapOf(VIDEO to PLAYBACK_STARTED))
                        hasStarted = true
                    }

                    // Track progress
                    progressStatuses.forEach { progress ->
                        if (sliderPosition >= progress && !loggedProgress.contains(progress)) {
                            val eventName =
                                if (progress == 0.95f) {
                                    LESSON_COMPLETED
                                } else {
                                    LESSON_PROGRESSED
                                }

                            DesignSystemAnalyticsProvider.track(eventName, mapOf(VIDEO to PROGRESS_STATUS))
                            loggedProgress.add(progress)
                        }
                    }
                }
            }
            delay(500)
        }
    }

    LaunchedEffect(exoPlayer) {
        totalDuration = exoPlayer.duration.coerceAtLeast(0L)
        exoPlayer.addListener(listener)
    }

    DisposableEffect(Unit) { onDispose { exoPlayer.removeListener(listener) } }

    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                    Lifecycle.Event.ON_RESUME -> if (exoPlayer.playWhenReady) exoPlayer.play()
                    else -> Unit
                }
            }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.removeListener(listener)
        }
    }

    fun seekBy(delta: Long) {
        val rawDuration = exoPlayer.duration
        if (rawDuration == C.TIME_UNSET || rawDuration <= 0L) return

        val newPos =
            (exoPlayer.currentPosition + delta)
                .coerceIn(0L, rawDuration)

        exoPlayer.seekTo(newPos)
        currentPosition = newPos
        sliderPosition = newPos.toFloat() / rawDuration
        showControls = true
    }

    Box(
        modifier =
            modifier
                .background(MivaColors.Secondary.BlueDeep)
                .clickable { showControls = !showControls },
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    layoutParams =
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                    useController = false

                    val blue = MivaColors.Secondary.BlueDeep.toArgb()
                    // shown until VIDEO starts playing
                    setShutterBackgroundColor(blue)
                    setBackgroundColor(blue)
                    (videoSurfaceView as? ViewGroup)?.setBackgroundColor(blue)

                    // show solid blue as artwork if the stream is audio-only or takes long to render first frame
                    defaultArtwork = blue.toDrawable()
                    setUseArtwork(true)
                }
            },
            update = { playerView -> playerView.player = exoPlayer },
            modifier = Modifier.fillMaxSize(),
        )

        if (showSpinner) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MivaColors.White,
            )
        }

        if (showControls) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isLandscape) {
                    Row(
                        modifier =
                            Modifier
                                .align(Alignment.TopStart)
                                .padding(Theme.spacing.x4),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ClickableSpanImage(
                            image = R.drawable.ic_circular_back_arrow,
                            onClick = onFullScreenToggle,
                        )
                        BasicText(
                            text = videoTitle.orEmpty(),
                            modifier = Modifier.padding(end = Theme.spacing.x8),
                            style =
                                Theme.typography.h1.copy(
                                    fontSize = 20.sp,
                                    lineHeight = 28.sp,
                                    letterSpacing = 0.1.sp,
                                    color = MivaColors.White,
                                ),
                        )
                    }
                }

                if (playbackState != Player.STATE_BUFFERING) {
                    Row(
                        modifier =
                            Modifier
                                .align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.x6),
                    ) {
                        ClickableSpanImage(
                            image = R.drawable.ic_rewind_10,
                            contentDescription = stringResource(R.string.rewind_10_seconds),
                            modifier = Modifier.size(Theme.spacing.x12),
                            tint = MivaColors.White,
                            onClick = { seekBy(-skipMs) },
                        )

                        ClickableSpanImage(
                            image = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play,
                            contentDescription =
                                stringResource(
                                    if (isPlaying) R.string.paused_icon else R.string.play_icon,
                                ),
                            modifier = Modifier.size(71.dp),
                            onClick = {
                                exoPlayer.playWhenReady = !isPlaying
                                showControls = true
                            },
                        )

                        ClickableSpanImage(
                            image = R.drawable.ic_forward_10,
                            contentDescription = stringResource(R.string.forward_10_seconds),
                            modifier = Modifier.size(Theme.spacing.x12),
                            tint = MivaColors.White,
                            onClick = { seekBy(skipMs) },
                        )
                    }
                }

                Column(
                    modifier =
                        Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .padding(horizontal = Theme.spacing.x4),
                ) {
                    VideoProgressBar(
                        progress = sliderPosition,
                        buffered = if (totalDuration > 0) exoPlayer.bufferedPosition.toFloat() / totalDuration else 0f,
                        onSeek = {
                            isUserScrubbing = true
                            sliderPosition = it
                        },
                        onSeekFinished = {
                            isUserScrubbing = false
                            exoPlayer.seekTo((sliderPosition * totalDuration).toLong())
                        },
                        enabled = totalDuration > 0,
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BasicText(
                            text = "${formatTime(currentPosition)} : ${formatTime(totalDuration)}",
                            style =
                                if (isLandscape) {
                                    Theme.typography.labelXs.copy(
                                        fontSize = 11.sp,
                                        lineHeight = 17.sp,
                                        letterSpacing = 0.1.sp,
                                        color = MivaColors.Secondary.Green50,
                                    )
                                } else {
                                    Theme.typography.headingM.copy(
                                        fontSize = 14.sp,
                                        lineHeight = 16.sp,
                                        letterSpacing = 0.1.sp,
                                        color = MivaColors.Secondary.Green50,
                                    )
                                },
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Box(
                            modifier =
                                Modifier
                                    .background(
                                        color = Color.Black.copy(alpha = 0.35f),
                                        shape = CircleShape,
                                    ).clickable {
                                        showSpeedMenu = true
                                        showControls = true
                                    }.padding(horizontal = 10.dp, vertical = 6.dp),
                        ) {
                            BasicText(
                                text = "${speedOptions[speedIndex]}x",
                                style =
                                    Theme.typography.headingM.copy(
                                        fontSize = 14.sp,
                                        lineHeight = 16.sp,
                                        letterSpacing = 0.1.sp,
                                        color = MivaColors.White,
                                    ),
                            )
                            DropdownMenu(
                                expanded = showSpeedMenu,
                                onDismissRequest = {
                                    showSpeedMenu = false
                                    showControls = true
                                },
                                modifier = Modifier.align(Alignment.BottomEnd),
                                properties =
                                    PopupProperties(
                                        focusable = true,
                                        dismissOnBackPress = true,
                                        dismissOnClickOutside = true,
                                        clippingEnabled = true,
                                    ),
                            ) {
                                speedOptions.forEachIndexed { i, s ->
                                    DropdownMenuItem(
                                        text = { BasicText("${s}x") },
                                        onClick = {
                                            applySpeed(i)
                                            showSpeedMenu = false
                                        },
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(Theme.spacing.x3))

                        ClickableSpanImage(
                            image =
                                if (isLandscape || isFullScreen) {
                                    R.drawable.ic_exit_fullscreen
                                } else {
                                    R.drawable.ic_fullscreen
                                },
                            contentDescription =
                                stringResource(
                                    if (isLandscape ||
                                        isFullScreen
                                    ) {
                                        R.string.exit_full_screen
                                    } else {
                                        R.string.full_screen
                                    },
                                ),
                            tint = MivaColors.White,
                            modifier = Modifier.size(if (isLandscape) Theme.spacing.x12 else Theme.spacing.x9),
                            onClick = onFullScreenToggle,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    buffered: Float,
    onSeek: (Float) -> Unit,
    onSeekFinished: () -> Unit,
    enabled: Boolean = true,
) {
    val trackHeight = 6.dp
    Box(
        Modifier.height(trackHeight),
    ) {
        Slider(
            value = progress.coerceIn(0f, 1f),
            onValueChange = onSeek,
            thumb = {
                Box(
                    Modifier
                        .size(Theme.spacing.x5)
                        .background(MivaColors.Secondary.Green500, CircleShape),
                )
            },
            onValueChangeFinished = onSeekFinished,
            enabled = enabled,
            modifier =
                modifier
                    .fillMaxWidth()
                    .drawBehind {
                        val strokeHeight = trackHeight.toPx()
                        val y = size.height / 2

                        val progressPx = size.width * progress
                        val bufferedPx = size.width * buffered

                        // Inactive track
                        drawRoundRect(
                            color = MivaColors.Secondary.Green50,
                            topLeft = Offset(0f, y - strokeHeight / 2),
                            size = Size(size.width, strokeHeight),
                            cornerRadius = CornerRadius(strokeHeight / 2),
                        )

                        // Buffered track
                        drawRoundRect(
                            color = MivaColors.Secondary.Green50,
                            topLeft = Offset(0f, y - strokeHeight / 2),
                            size = Size(bufferedPx, strokeHeight),
                            cornerRadius = CornerRadius(strokeHeight / 2),
                        )

                        // Active progress track
                        drawRoundRect(
                            color = MivaColors.Secondary.Green500,
                            topLeft = Offset(0f, y - strokeHeight / 2),
                            size = Size(progressPx, strokeHeight),
                            cornerRadius = CornerRadius(strokeHeight / 2),
                        )
                    },
            colors =
                SliderDefaults.colors(
                    thumbColor = MivaColors.Secondary.Green500,
                    activeTrackColor = Color.Transparent,
                    inactiveTrackColor = Color.Transparent,
                ),
        )
    }
}

fun formatTime(ms: Long): String {
    if (ms <= 0) return "00:00"
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}

fun Context.findActivity(): Activity? =
    when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
