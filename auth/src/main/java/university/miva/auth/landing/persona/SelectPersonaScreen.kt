package university.miva.auth.landing.persona

import Theme
import android.annotation.SuppressLint
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.koinViewModel
import university.miva.auth.R
import university.miva.auth.api.AuthAnalytics
import university.miva.auth.api.AuthConstants
import university.miva.auth.api.AuthFeatureConfig
import university.miva.auth.api.AuthNavigationHandler
import university.miva.auth.api.AuthPersona
import university.miva.auth.api.AuthWebLauncher
import university.miva.auth.landing.persona.composables.SpannableText
import university.miva.auth.landing.viewModel.AuthUiState
import university.miva.auth.landing.viewModel.AuthViewModel
import university.miva.designsystem.components.CustomizeSystemBars
import university.miva.designsystem.components.divider.Divider
import university.miva.designsystem.components.image.Image
import university.miva.designsystem.components.snackbar.InternetSnackbarHost
import university.miva.designsystem.components.text.BasicText
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.R as DesignSystemR

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectPersonaScreen(navController: NavController) {
    val viewModel: AuthViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val currentSession = viewModel.getCurrentSession()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val isOnline by viewModel.isOnline.collectAsState()
    val navigationHandler: AuthNavigationHandler by inject()

    LaunchedEffect(uiState) {
        when (uiState) {
            is AuthUiState.Success -> {
                if (currentSession?.enrollmentStatus == AuthConstants.APPLICANT_ENROLLED) {
                    navigationHandler.openApplicantDashboard(navController)
                } else {
                    navigationHandler.openStudentDashboard(navController)
                }
            }

            is AuthUiState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar((uiState as AuthUiState.Error).message)
                }
            }

            else -> {}
        }
    }

    Scaffold(snackbarHost = { InternetSnackbarHost(snackbarHostState) }) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomizeSystemBars(
                darkIcons = false,
            )
            VideoBackground()
            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .background(MivaColors.Primary.MainBlue.copy(alpha = 0.7f))
                        .padding(top = 50.dp),
            )

            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(end = Theme.spacing.x10, top = Theme.spacing.x22),
            ) {
                AnimatedPersonaSelection(navController, isOnline, coroutineScope, snackbarHostState)
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoBackground() {
    val context = LocalContext.current
    val videoUri = "android.resource://${context.packageName}/${DesignSystemR.raw.onboarding_video}"

    val exoPlayer =
        remember {
            ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri(videoUri)
                setMediaItem(mediaItem)
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_ALL
                volume = 0f
                prepare()
            }
        }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        },
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
fun PersonaItem(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick.invoke() }
                .padding(start = Theme.spacing.x3, top = Theme.spacing.x6, bottom = Theme.spacing.x6),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BasicText(
            text = text,
            style =
                Theme.typography.h4.copy(
                    color = MivaColors.White,
                    fontWeight = FontWeight.Bold,
                ),
        )
        Image(
            image = R.drawable.ic_arrow_right_bg,
            contentDescription = "",
            modifier = Modifier.size(Theme.spacing.x6),
        )
    }
}

@Composable
fun AnimatedPersonaSelection(
    navController: NavController,
    isOnline: Boolean,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    val authFeatureConfig: AuthFeatureConfig by inject()
    val authWebLauncher: AuthWebLauncher by inject()
    val navigationHandler: AuthNavigationHandler by inject()
    val authAnalytics: AuthAnalytics by inject()
    val context = LocalContext.current
    var isVisible by remember { mutableStateOf(false) }

    val transition = updateTransition(targetState = isVisible, label = "PersonaTransition")
    val casLoginUrl = "${authFeatureConfig.casBaseUrl}cas/login?service=miva://login"

    val translationX by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 600, easing = FastOutSlowInEasing) },
        label = "TranslationX",
    ) { visible ->
        if (visible) 0f else -1000f
    }

    LaunchedEffect(Unit) {
        delay(500)
        isVisible = true
    }

    DisposableEffect(Unit) {
        onDispose {
            isVisible = false
        }
    }
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .graphicsLayer(translationX = translationX),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(MivaColors.Primary.MainBlue)
                    .padding(Theme.spacing.x9),
        ) {
            Image(
                image = R.drawable.miva_uni_logo,
                contentDescription = stringResource(R.string.miva_logo),
                modifier = Modifier.width(240.dp).height(78.dp),
            )
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(MivaColors.Secondary.BlueLight.copy(alpha = 0.7f))
                    .padding(Theme.spacing.x6),
        ) {
            BasicText(
                text = stringResource(R.string.which_persona_fits_you_best),
                style =
                    Theme.typography.bodyM.copy(
                        color = MivaColors.White,
                    ),
                modifier = Modifier.padding(start = Theme.spacing.x3, bottom = Theme.spacing.x6),
            )
            PersonaItem(stringResource(R.string.prospective_student)) {
                authAnalytics.trackPersonaSelected(AuthPersona.PROSPECTIVE_STUDENT)
                navigationHandler.openGuestExperience(navController)
            }
            Divider(color = MivaColors.Secondary.OffWhite, thickness = 1.dp)
            PersonaItem(stringResource(R.string.applicant)) {
                if (isOnline) {
                    authAnalytics.trackPersonaSelected(AuthPersona.APPLICANT)
                    authWebLauncher.openLogin(context, casLoginUrl)
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(university.miva.designsystem.R.string.internet_error),
                        )
                    }
                }
            }
            Divider(color = MivaColors.Secondary.OffWhite, thickness = 1.dp)
            PersonaItem(stringResource(R.string.current_student)) {
                if (isOnline) {
                    authAnalytics.trackPersonaSelected(AuthPersona.CURRENT_STUDENT)
                    authWebLauncher.openLogin(context = context, url = casLoginUrl)
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(university.miva.designsystem.R.string.internet_error),
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(Theme.spacing.x6))

        Row(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.x2),
            modifier = Modifier.padding(start = Theme.spacing.x8, bottom = Theme.spacing.x6),
        ) {
            Image(
                image = R.drawable.license_logo,
                contentDescription = stringResource(R.string.license_logo),
                modifier = Modifier.size(Theme.spacing.x10),
            )
            SpannableText()
        }
    }
}
