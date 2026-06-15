package university.miva.guest.ui

import Theme
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.koinViewModel
import university.miva.designsystem.components.CustomizeSystemBars
import university.miva.designsystem.components.button.AppBackButton
import university.miva.designsystem.components.image.ClickableSpanImage
import university.miva.designsystem.components.loading.LoadingDialog
import university.miva.designsystem.components.search.AppSearchBar
import university.miva.designsystem.components.snackbar.SnackbarHostWithCustomSnackbar
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.Routes
import university.miva.designsystem.util.encodeToJson
import university.miva.designsystem.util.encodeToUrl
import university.miva.designsystem.util.extractYouTubeId
import university.miva.guest.api.GuestAnalytics
import university.miva.guest.api.GuestAnalyticsEvents.APPLY
import university.miva.guest.api.GuestAnalyticsEvents.POSTGRAD_PROGRAM_VIEW
import university.miva.guest.api.GuestAnalyticsEvents.UNDERGRAD_PROGRAM_VIEW
import university.miva.guest.api.GuestAnalyticsParams.ALL_POSTGRADUATE_PROGRAMS
import university.miva.guest.api.GuestAnalyticsParams.ALL_UNDERGRADUATE_PROGRAMS
import university.miva.guest.api.GuestAnalyticsParams.APPLY_FOR_ADMISSION
import university.miva.guest.api.GuestAnalyticsParams.CLICK
import university.miva.guest.api.GuestAnalyticsParams.DIRECT_ENTRY_APPLICATION
import university.miva.guest.api.GuestAnalyticsParams.TRANSFER_TO_MIVA
import university.miva.guest.api.GuestFeatureConfig
import university.miva.guest.api.GuestLinks
import university.miva.guest.api.GuestWebLauncher
import university.miva.guest.ui.components.ApplyForAdmissionBanner
import university.miva.guest.ui.components.ChancellorAddressView
import university.miva.guest.ui.components.ChancellorsAddressCard
import university.miva.guest.ui.components.DirectEntryAdmissionView
import university.miva.guest.ui.components.GuestSignInView
import university.miva.guest.ui.components.InterUniversityTransferView
import university.miva.guest.ui.components.LearnMoreCard
import university.miva.guest.ui.components.ProgramSection
import university.miva.guest.ui.components.QuickAnswerSection
import university.miva.guest.ui.components.StudentCommunityView
import university.miva.guest.ui.components.TestimonialCarousel
import university.miva.guest.ui.utils.isPostGraduatesCode
import university.miva.guest.ui.viewmodel.GuestUiState
import university.miva.guest.ui.viewmodel.GuestViewModel
import university.miva.guest.ui.viewmodel.matchesQuery
import university.miva.guest.ui.viewmodel.normalize
import university.miva.designsystem.R as DesignSystemR

@OptIn(FlowPreview::class, ExperimentalMaterialApi::class)
@Composable
fun GuestScreen(navController: NavController) {
    val viewModel: GuestViewModel = koinViewModel()
    val guestFeatureConfig: GuestFeatureConfig by inject()
    val guestWebLauncher: GuestWebLauncher by inject()
    val guestAnalytics: GuestAnalytics by inject()
    val uiState by viewModel.uiState.collectAsState()
    val testimonials by viewModel.testimonials.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showGuestProfile by remember { mutableStateOf(false) }
    var showChancellorAddress by remember { mutableStateOf(false) }
    var showDirectEntryAdmissions by remember { mutableStateOf(false) }
    var showTransferToMiva by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val activity = LocalContext.current as? Activity
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val view = LocalView.current
    var carouselBounds by remember { mutableStateOf<Rect?>(null) }
    var carouselTopY by remember { mutableFloatStateOf(0f) }
    val shouldAnimate = remember { mutableStateOf(false) }
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val snackbarHostState = remember { SnackbarHostState() }
    val casLoginUrl = "${guestFeatureConfig.casBaseUrl}cas/login?service=miva://login"
    var isError by remember { mutableStateOf(false) }
    val allProgrammes = (uiState as? GuestUiState.ProgrammesSuccess)?.programmes
    val countryCode = (uiState as? GuestUiState.ProgrammesSuccess)?.countryCode
    val isOnline by viewModel.isOnline.collectAsState()
    val filtered =
        remember(searchQuery, allProgrammes) {
            val q = searchQuery.trim().normalize()
            if (q.isEmpty()) allProgrammes else allProgrammes?.filter { it.matchesQuery(q) }
        }

    val underGraduatePrograms =
        remember(filtered) {
            filtered?.filterNot { it.isPostGraduatesCode() }
        }
    val postGraduatePrograms =
        remember(filtered) {
            filtered?.filter { it.isPostGraduatesCode() }
        }

    var liveQuery by remember { mutableStateOf("") }
    LaunchedEffect(liveQuery) {
        snapshotFlow { liveQuery }.debounce(250).collect { searchQuery = it }
    }

    fun onFullscreenToggle() {
        activity?.requestedOrientation =
            if (isPortrait) {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
    }

    fun onPortrait() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    val refreshing = uiState is GuestUiState.Loading
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = refreshing,
            onRefresh = {
                viewModel.refreshData()
            },
        )

    LaunchedEffect(uiState) {
        when (uiState) {
            is GuestUiState.Error -> {
                isError = true
                coroutineScope.launch {
                    snackbarHostState.showSnackbar((uiState as GuestUiState.Error).errorMessage)
                }
            }
            else -> {
                isError = false
            }
        }
    }

    BoxWithConstraints(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
    ) {
        val maxWidth = this.maxWidth

        Scaffold(
            snackbarHost = {
                SnackbarHostWithCustomSnackbar(
                    snackbarHostState,
                    isError = isError,
                    isInternetError = !isOnline,
                )
            },
            containerColor = MivaColors.Secondary.Background,
            topBar = {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = Theme.spacing.x4),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppBackButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    )
                    Image(
                        painterResource(id = DesignSystemR.drawable.ic_profile),
                        contentDescription = stringResource(DesignSystemR.string.profile),
                        modifier =
                            Modifier
                                .clip(CircleShape)
                                .clickable { showGuestProfile = true }
                                .padding(Theme.spacing.x3),
                    )
                }
            },
        ) { innerPadding ->

            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState),
            ) {
                CustomizeSystemBars(
                    darkIcons = true,
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val titleFontSize =
                        when {
                            maxWidth < 350.dp -> 30.sp
                            maxWidth < 400.dp -> 32.sp
                            maxWidth < 500.dp -> 34.sp
                            maxWidth < 600.dp -> 36.sp
                            else -> 50.sp
                        }

                    LazyColumn(
                        state = listState,
                        modifier =
                            Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                    ) {
                        item {
                            GuestSignInView(
                                visible = showGuestProfile,
                                onDismiss = { showGuestProfile = false },
                                onEmailSignInClick = {
                                    showGuestProfile = false
                                    isError = false
                                    if (isOnline) {
                                        guestWebLauncher.openAuthWebView(context, casLoginUrl)
                                    } else {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = context.getString(DesignSystemR.string.internet_error),
                                            )
                                        }
                                    }
                                },
                            )

                            ChancellorAddressView(
                                visible = showChancellorAddress,
                                onDismiss = { showChancellorAddress = false },
                            )

                            DirectEntryAdmissionView(
                                visible = showDirectEntryAdmissions,
                                onDismiss = { showDirectEntryAdmissions = false },
                                onApplyClick = {
                                    isError = false
                                    if (isOnline) {
                                        guestAnalytics.log(APPLY, mapOf(CLICK to DIRECT_ENTRY_APPLICATION))
                                        guestWebLauncher.openWebView(context, url = GuestLinks.APPLY_WEBSITE)
                                    } else {
                                        showDirectEntryAdmissions = false
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = context.getString(DesignSystemR.string.internet_error),
                                            )
                                        }
                                    }
                                },
                            )

                            InterUniversityTransferView(
                                visible = showTransferToMiva,
                                onDismiss = { showTransferToMiva = false },
                                onTransferToMivaClick = {
                                    isError = false
                                    if (isOnline) {
                                        guestAnalytics.log(APPLY, mapOf(CLICK to TRANSFER_TO_MIVA))
                                        guestWebLauncher.openWebView(context, url = GuestLinks.APPLY_WEBSITE)
                                    } else {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message =
                                                    context.getString(
                                                        university.miva.designsystem.R.string.internet_error,
                                                    ),
                                            )
                                        }
                                    }
                                },
                            )

                            Column(
                                modifier =
                                    Modifier
                                        .fillMaxWidth(),
                            ) {
                                Column(
                                    modifier =
                                        Modifier
                                            .padding(Theme.spacing.x4),
                                ) {
                                    Text(
                                        text = stringResource(DesignSystemR.string.join_thousands_of_miva_students),
                                        style =
                                            Theme.typography.h1.copy(
                                                fontSize = titleFontSize,
                                            ),
                                    )
                                    Spacer(modifier = Modifier.height(Theme.spacing.x5))

                                    AppSearchBar(
                                        query = liveQuery,
                                        hintText = stringResource(DesignSystemR.string.search_for_a_programme),
                                        onQueryChange = { liveQuery = it },
                                        onLeadingIconClick = {},
                                        onClick = {},
                                        onSearch = {},
                                    )
                                }

                                Spacer(modifier = Modifier.height(Theme.spacing.x4))
                                if (underGraduatePrograms != null) {
                                    ProgramSection(
                                        title = stringResource(DesignSystemR.string.undergraduate_program),
                                        programmes = underGraduatePrograms,
                                        countryCode = countryCode ?: "",
                                        goToViewAll = {
                                            guestAnalytics.log(
                                                UNDERGRAD_PROGRAM_VIEW,
                                                mapOf(
                                                    CLICK to ALL_UNDERGRADUATE_PROGRAMS,
                                                ),
                                            )
                                            navController.navigate(Routes.UNDERGRADUATE)
                                        },
                                        goToProgramDetails = { program ->
                                            val programmeJson = program.encodeToJson().encodeToUrl()
                                            navController.navigate("${Routes.FACULTY_DETAILS}/$programmeJson")
                                        },
                                    )
                                    Spacer(modifier = Modifier.height(Theme.spacing.x8))
                                }

                                if (postGraduatePrograms != null) {
                                    ProgramSection(
                                        title = stringResource(DesignSystemR.string.postgraduate_program),
                                        countryCode = countryCode ?: "",
                                        programmes = postGraduatePrograms,
                                        goToViewAll = {
                                            guestAnalytics.log(
                                                POSTGRAD_PROGRAM_VIEW,
                                                mapOf(
                                                    CLICK to ALL_POSTGRADUATE_PROGRAMS,
                                                ),
                                            )
                                            navController.navigate(Routes.POSTGRADUATE)
                                        },
                                        goToProgramDetails = { program ->
                                            val programmeJson = program.encodeToJson().encodeToUrl()
                                            navController.navigate("${Routes.FACULTY_DETAILS}/$programmeJson")
                                        },
                                    )
                                }

                                Spacer(modifier = Modifier.height(Theme.spacing.x12))

                                ApplyForAdmissionBanner(
                                    onClick = {
                                        isError = false
                                        if (isOnline) {
                                            guestAnalytics.log(APPLY, mapOf(CLICK to APPLY_FOR_ADMISSION))
                                            guestWebLauncher.openWebView(
                                                context,
                                                url = GuestLinks.GET_STARTED,
                                            )
                                        } else {
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = context.getString(DesignSystemR.string.internet_error),
                                                )
                                            }
                                        }
                                    },
                                )
                                Spacer(modifier = Modifier.height(Theme.spacing.x8))
                                if (testimonials.isNotEmpty()) {
                                    Box(
                                        modifier =
                                            Modifier
                                                .offset(y = (-54).dp)
                                                .bringIntoViewRequester(bringIntoViewRequester)
                                                .onGloballyPositioned { layoutCoordinates ->
                                                    carouselBounds = layoutCoordinates.boundsInWindow()
                                                    carouselTopY = layoutCoordinates.boundsInWindow().top
                                                },
                                    ) {
                                        TestimonialCarousel(
                                            testimonials = testimonials,
                                            shouldAnimate = shouldAnimate.value,
                                            onPlay = { videoUrl, thumbnail ->
                                                isError = false
                                                if (isOnline) {
                                                    shouldAnimate.value = true
                                                    viewModel.selectedVideoUrl = videoUrl
                                                    viewModel.selectedVideoThumbnail = thumbnail
                                                    // Scroll to center
                                                    coroutineScope.launch {
                                                        carouselBounds?.let { bounds ->
                                                            val viewHeightPx = view.height.toFloat()
                                                            val centerOffset =
                                                                carouselTopY - (viewHeightPx / 2.5).toInt()
                                                            val scrollBy =
                                                                centerOffset.coerceAtLeast(
                                                                    -listState.firstVisibleItemScrollOffset.toFloat(),
                                                                )
                                                            listState.animateScrollBy(scrollBy)
                                                        }
                                                    }
                                                } else {
                                                    coroutineScope.launch {
                                                        snackbarHostState.showSnackbar(
                                                            message =
                                                                context.getString(
                                                                    DesignSystemR.string.internet_error,
                                                                ),
                                                        )
                                                    }
                                                }
                                            },
                                        )
                                    }
                                }
                                Box(
                                    modifier =
                                        Modifier
                                            .padding(horizontal = Theme.spacing.x4)
                                            .offset(y = (-24).dp)
                                            .clickable { showChancellorAddress = true },
                                ) {
                                    ChancellorsAddressCard()
                                }
                                Spacer(modifier = Modifier.height(Theme.spacing.x4))
                                StudentCommunityView()
                                QuickAnswerSection()
                                LearnMoreCard(
                                    onClickDirectEntry = { showDirectEntryAdmissions = true },
                                    onClickTransfers = {
                                        showTransferToMiva = true
                                    },
                                )
                            }
                        }
                    }

                    if (uiState is GuestUiState.Loading) {
                        LoadingDialog()
                    }
                }

                if (viewModel.selectedVideoUrl != null) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.7f))
                                .clickable { viewModel.selectedVideoUrl = null },
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .wrapContentSize()
                                    .fillMaxWidth(0.9f)
                                    .fillMaxHeight(if (isPortrait) 0.34f else 0.78f),
                        ) {
                            YouTubePlayerOverlay(
                                viewModel = viewModel,
                                videoUrl = viewModel.selectedVideoUrl.orEmpty(),
                                isPortrait = isPortrait,
                                onDismiss = {
                                    viewModel.selectedVideoUrl = null
                                    shouldAnimate.value = false
                                    onPortrait()
                                },
                                onFullscreenToggle = { onFullscreenToggle() },
                            )
                        }
                    }
                } else {
                    shouldAnimate.value = false
                    onPortrait()
                }

                PullRefreshIndicator(
                    refreshing = refreshing,
                    state = pullRefreshState,
                    modifier =
                        Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = innerPadding.calculateTopPadding()),
                    contentColor = MivaColors.Secondary.BlueDeep,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun YouTubePlayerOverlay(
    viewModel: GuestViewModel,
    videoUrl: String,
    isPortrait: Boolean,
    onDismiss: () -> Unit,
    onFullscreenToggle: () -> Unit,
) {
    val videoId = remember { extractYouTubeId(videoUrl) }
    val context = LocalContext.current
    var isPlayerReady by remember { mutableStateOf(false) }
    var yTPlayer by remember { mutableStateOf<YouTubePlayer?>(null) }
    var showThumbnail by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var playWhenReady by remember { mutableStateOf(false) }

    val windowSizeClass = calculateWindowSizeClass(activity = LocalContext.current as Activity)

    val isExpanded = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val isMedium = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium

    val youTubePlayerView =
        remember {
            YouTubePlayerView(context).apply {
                enableAutomaticInitialization = false
            }
        }

    // Main player initialization and cleanup
    DisposableEffect(videoId) {
        val listener =
            object : AbstractYouTubePlayerListener() {
                private var lastKnownPosition: Float = viewModel.playbackPosition
                private var isPlaying: Boolean = viewModel.wasPlaying

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    yTPlayer = youTubePlayer
                    youTubePlayer.loadVideo(videoId.orEmpty(), lastKnownPosition)
                    youTubePlayer.play()
                    isPlayerReady = true
                    isLoading = false
                }

                override fun onCurrentSecond(
                    youTubePlayer: YouTubePlayer,
                    second: Float,
                ) {
                    lastKnownPosition = second
                    viewModel.updatePlaybackState(second, isPlaying)
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState,
                ) {
                    isPlaying = state == PlayerConstants.PlayerState.PLAYING
                    viewModel.updatePlaybackState(lastKnownPosition, isPlaying)
                }

                override fun onVideoLoadedFraction(
                    youTubePlayer: YouTubePlayer,
                    loadedFraction: Float,
                ) {
                    super.onVideoLoadedFraction(youTubePlayer, loadedFraction)
                }
            }

        youTubePlayerView.initialize(
            listener,
            IFramePlayerOptions
                .Builder()
                .controls(1)
                .build(),
        )

        onDispose {
            youTubePlayerView.release()
            if (viewModel.selectedVideoUrl == null) {
                viewModel.clearPlaybackState()
            }
        }
    }

    // Handle orientation changes
    DisposableEffect(isPortrait) {
        if (isPlayerReady) {
            yTPlayer?.let { player ->
                player.seekTo(viewModel.playbackPosition)
                if (viewModel.wasPlaying) {
                    player.play()
                }
            }
        }
        onDispose {}
    }

    fun onPlayClick() {
        if (isPlayerReady) {
            showThumbnail = false
            yTPlayer?.loadVideo(videoId.orEmpty(), viewModel.playbackPosition)
            yTPlayer?.play()
        } else {
            playWhenReady = true
            showThumbnail = false
            isLoading = true
        }
    }

    Box(modifier = Modifier.wrapContentSize()) {
        AndroidView(
            factory = { youTubePlayerView },
        )
        ClickableSpanImage(
            DesignSystemR.drawable.ic_close_youtube,
            modifier =
                Modifier
                    .align(Alignment.TopEnd),
        ) {
            onDismiss()
        }
    }
}

@Preview(
    name = "Landscape",
    group = "BoxWithConstraints",
    widthDp = 1248,
    heightDp = 360,
)
@Preview(
    name = "Portrait600",
    group = "BoxWithConstraints",
    widthDp = 601,
    heightDp = 720,
)
@Preview(
    name = "Portrait400",
    group = "BoxWithConstraints",
    widthDp = 400,
    heightDp = 720,
)
@Preview(
    name = "Portrait350",
    group = "BoxWithConstraints",
    widthDp = 300,
    heightDp = 640,
)
@Composable
fun TitleText() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
    ) {
        val bWCScope = this
        val maxWidth = bWCScope.maxWidth
        val titleFontSize =
            when {
                maxWidth < 350.dp -> 34.sp
                maxWidth < 400.dp -> 34.sp
                maxWidth < 600.dp -> 36.sp
                else -> 50.sp
            }
        Text(
            text = stringResource(DesignSystemR.string.join_thousands_of_miva_students),
            style =
                Theme.typography.h1.copy(
                    fontSize = titleFontSize,
                ),
        )
    }
}
