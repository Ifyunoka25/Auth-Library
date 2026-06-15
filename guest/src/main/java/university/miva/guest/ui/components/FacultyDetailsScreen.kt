package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import university.miva.designsystem.R
import university.miva.designsystem.components.button.AppBackButton
import university.miva.designsystem.components.button.AppButton
import university.miva.designsystem.components.snackbar.SnackbarHostWithCustomSnackbar
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.Constants
import university.miva.guest.api.GuestLinks
import university.miva.guest.api.GuestWebLauncher
import university.miva.guest.ui.utils.isPostGraduates
import university.miva.guest.ui.viewmodel.GuestDetailsUiState
import university.miva.guest.ui.viewmodel.GuestDetailsViewModel
import university.miva.guest.ui.viewmodel.models.ProgrammeInfo
import university.miva.designsystem.R as DesignSystemR

@Composable
fun FacultyDetailsScreen(
    programme: ProgrammeInfo,
    navController: NavController,
) {
    val viewModel: GuestDetailsViewModel = koinViewModel()
    val guestWebLauncher: GuestWebLauncher by inject()
    val uiState by viewModel.uiState.collectAsState()
    val notice by viewModel.notice.collectAsState()
    val code by viewModel.code.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val isOnline by viewModel.isOnline.collectAsState()
    val tabs = listOf("Details", "Curriculum", "Requirements", "Tuition")
    val snackbarHostState = remember { SnackbarHostState() }
    val url =
        when {
            programme.isPostGraduates() -> GuestLinks.MASTERS_APPLICATION
            else -> GuestLinks.APPLY_WEBSITE
        }
    val coroutineScope = rememberCoroutineScope()
    val tuitionDescription =
        when {
            programme.isPostGraduates() ->
                stringResource(
                    DesignSystemR.string.miva_uni_offers_a_flexible_payment_spread_out,
                )
            else -> stringResource(DesignSystemR.string.miva_uni_offers_a_flexible_payment)
        }

    LaunchedEffect(Unit) {
        Timber.tag("FacultyDetailsScreen").d("Programme: $programme")
        Timber.tag("FacultyDetailsScreen").d("programme.image: ${programme.image}")
        viewModel.getGuestCurriculum(programme)
        viewModel.loadNotice()
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val bWCScope = this
        val maxWidth = bWCScope.maxWidth
        val isTablet = maxWidth > Constants.phoneWidth

        Scaffold(
            snackbarHost = {
                SnackbarHostWithCustomSnackbar(
                    snackbarHostState,
                    isError = false,
                    isInternetError = !isOnline,
                )
            },
            containerColor = MivaColors.Secondary.Grey50,
            modifier =
                Modifier
                    .padding(top = 50.dp),
            topBar = {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppBackButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    )
                    Spacer(modifier = Modifier.width(Theme.spacing.x2))
                    Text(
                        text = programme.name,
                        style =
                            Theme.typography.bodyM.copy(
                                color = MivaColors.TextColors.BlueDeep,
                            ),
                        textAlign = TextAlign.Start,
                    )
                }
            },
        ) { paddingValues ->

            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
            ) {
                programme.image?.let {
                    val painter =
                        rememberAsyncImagePainter(
                            model = it,
                            error = painterResource(R.drawable.place_holder_image),
                            fallback = painterResource(R.drawable.place_holder_image),
                        )
                    Image(
                        painter = painter,
                        contentDescription = stringResource(DesignSystemR.string.info_icon),
                        contentScale = ContentScale.Crop,
                        modifier =
                            Modifier
                                .height(if (isTablet) 500.dp else 220.dp)
                                .fillMaxWidth(),
                    )
                }

                if (isTablet) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.White,
                        contentColor = MivaColors.TextColors.BlueDeep,
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = MivaColors.TextColors.BlueDeep,
                                height = Theme.spacing.x1,
                            )
                        },
                        divider = {
                            HorizontalDivider(
                                color = MivaColors.Secondary.TabBorderColor,
                                thickness = Theme.spacing.x1,
                            )
                        },
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Text(
                                        text = tab,
                                        style =
                                            Theme.typography.bodyS.copy(
                                                color = MivaColors.TextColors.BlueDeep,
                                            ),
                                        maxLines = 1,
                                    )
                                },
                            )
                        }
                    }
                } else {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color.White,
                        contentColor = MivaColors.TextColors.BlueDeep,
                        edgePadding = 0.dp,
                        indicator = { tabPositions ->
                            SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = MivaColors.TextColors.BlueDeep,
                                height = Theme.spacing.x1,
                            )
                        },
                        divider = {
                            HorizontalDivider(
                                color = MivaColors.Secondary.TabBorderColor,
                                thickness = Theme.spacing.x1,
                            )
                        },
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Text(
                                        text = tab,
                                        style =
                                            Theme.typography.bodyS.copy(
                                                color = MivaColors.TextColors.BlueDeep,
                                            ),
                                        maxLines = 1,
                                    )
                                },
                            )
                        }
                    }
                }

                // Tab Content
                when (selectedTabIndex) {
                    0 ->
                        ProgrammeDetailTab(
                            title = programme.name,
                            notice = notice,
                            countryCode = code,
                            programme = programme,
                            onClick = {
                                if (isOnline) {
                                    guestWebLauncher.openWebView(context = context, url = url)
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = context.getString(R.string.internet_error),
                                        )
                                    }
                                }
                            },
                        )

                    1 ->
                        when (uiState) {
                            is GuestDetailsUiState.CurriculumSuccess -> {
                                val curriculums = (uiState as GuestDetailsUiState.CurriculumSuccess).curriculums
                                ProgrammeCurriculumTab(
                                    title = stringResource(DesignSystemR.string.programme_outline),
                                    curriculums = curriculums,
                                    programmeInfo = programme,
                                    onClick = {
                                        if (isOnline) {
                                            guestWebLauncher.openWebView(context = context, url = url)
                                        } else {
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar(
                                                    message = context.getString(R.string.internet_error),
                                                )
                                            }
                                        }
                                    },
                                )
                            }

                            is GuestDetailsUiState.Error -> {
                                ErrorStateScreen(
                                    message = (uiState as GuestDetailsUiState.Error).errorMessage,
                                ) {
                                    viewModel.getGuestCurriculum(programme)
                                    viewModel.loadNotice()
                                }
                            }

                            GuestDetailsUiState.Loading -> {
                                Text(
                                    modifier = Modifier.padding(Theme.spacing.x4),
                                    text = stringResource(R.string.loading_curriculum),
                                    color = MivaColors.TextColors.BlueDeep,
                                )
                            }
                        }

                    2 ->
                        programme.programmeRequirementsInfo?.let {
                            ProgrammeRequirementTab(
                                title = stringResource(DesignSystemR.string.admission_requirements),
                                programmeRequirements = it,
                                onClick = {
                                    if (isOnline) {
                                        guestWebLauncher.openWebView(context = context, url = url)
                                    } else {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = context.getString(R.string.internet_error),
                                            )
                                        }
                                    }
                                },
                            )
                        }

                    3 ->
                        ProgrammeTuitionTab(
                            title = stringResource(DesignSystemR.string.payment_plans),
                            programme = programme,
                            countryCode = code,
                            body = tuitionDescription,
                            onClick = {
                                if (isOnline) {
                                    guestWebLauncher.openWebView(context = context, url = url)
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = context.getString(R.string.internet_error),
                                        )
                                    }
                                }
                            },
                        )
                }
            }
        }
    }
}

@Composable
fun ApplyNowButton(onClick: () -> Unit) {
    AppButton(
        title = stringResource(DesignSystemR.string.apply_now),
        horizontalPadding = 0.dp,
        verticalPadding = 0.dp,
        onClick = onClick,
    )
}
