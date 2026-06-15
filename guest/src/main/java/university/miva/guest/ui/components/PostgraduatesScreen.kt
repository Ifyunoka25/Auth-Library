package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import org.koin.androidx.compose.koinViewModel
import university.miva.designsystem.R
import university.miva.designsystem.components.ErrorView
import university.miva.designsystem.components.loading.LoadingDialog
import university.miva.designsystem.components.search.AppSearchBar
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.Constants
import university.miva.designsystem.util.DEFAULT_COUNTRY_CODE
import university.miva.designsystem.util.Routes
import university.miva.designsystem.util.encodeToJson
import university.miva.designsystem.util.encodeToUrl
import university.miva.designsystem.util.formatAmount
import university.miva.guest.ui.utils.isPostGraduatesCode
import university.miva.guest.ui.viewmodel.GuestUiState
import university.miva.guest.ui.viewmodel.GuestViewModel
import university.miva.guest.ui.viewmodel.matchesQuery
import university.miva.guest.ui.viewmodel.models.ProgrammeInfo
import university.miva.guest.ui.viewmodel.models.postGradsProgrammeLogoMap
import university.miva.guest.ui.viewmodel.normalize
import university.miva.designsystem.R as DesignSystemR

@OptIn(FlowPreview::class)
@Composable
fun PostGraduateScreen(navController: NavController) {
    val viewModel: GuestViewModel = koinViewModel()
    val listState = rememberLazyListState()
    var searchQuery by remember { mutableStateOf("") }
    var liveQuery by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(liveQuery) {
        snapshotFlow { liveQuery }.debounce(250).collect { searchQuery = it }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MivaColors.Secondary.DividerColor)
                .padding(top = 50.dp)
                .padding(horizontal = Theme.spacing.x4),
    ) {
        when (val state = uiState) {
            is GuestUiState.Loading -> {
                LoadingDialog()
            }

            is GuestUiState.ProgrammesSuccess -> {
                val programmes = state.programmes
                val countryCode = state.countryCode

                val postGrads =
                    remember(programmes) {
                        programmes.filter { it.isPostGraduatesCode() }
                    }

                val filtered =
                    remember(postGrads, searchQuery) {
                        val query = searchQuery.trim().normalize()
                        if (query.isEmpty()) postGrads else postGrads.filter { it.matchesQuery(query) }
                    }

                Column(
                    modifier =
                        Modifier
                            .fillMaxSize(),
                ) {
                    Spacer(modifier = Modifier.height(Theme.spacing.x4))
                    AppSearchBar(
                        query = liveQuery,
                        hintText = stringResource(DesignSystemR.string.search_masters_programmes),
                        image = DesignSystemR.drawable.ic_arrow_left,
                        onQueryChange = { liveQuery = it },
                        onLeadingIconClick = {
                            navController.popBackStack()
                        },
                        onClick = {},
                        onSearch = {},
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.x4))
                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(Theme.spacing.x4),
                    ) {
                        if (filtered.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = stringResource(DesignSystemR.string.no_postgraduate_programmes_found),
                                        style = Theme.typography.bodyM,
                                    )
                                }
                            }
                        } else {
                            items(filtered) { programme ->
                                ProgrammeInfoCard(
                                    programme = programme,
                                    countryCode = countryCode,
                                    onSchoolProgrammeClick = { selectedProgramme ->
                                        val encodedSchoolProgramme = selectedProgramme.encodeToJson().encodeToUrl()
                                        navController.navigate("${Routes.FACULTY_DETAILS}/$encodedSchoolProgramme")
                                    },
                                )
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(Theme.spacing.x4))
                        }
                    }
                }
            }

            is GuestUiState.Error -> {
                ErrorView(
                    message = (uiState as GuestUiState.Error).errorMessage,
                    onRetry = { viewModel.loadFaculties() },
                )
            }

            is GuestUiState.Success -> {}
        }
    }
}

@Composable
fun ProgrammeInfoCard(
    programme: ProgrammeInfo,
    countryCode: String,
    onSchoolProgrammeClick: (ProgrammeInfo) -> Unit,
) {
    val isFeePerSemesterZeroOrNull =
        programme.tuitionFeePerSemesterInNaira == null ||
            programme.tuitionFeePerSemesterInNaira == 0.0 ||
            programme.tuitionFeePerSemesterInDollar == null ||
            programme.tuitionFeePerSemesterInDollar == 0.0

    val isYearlyTuitionZeroOrNull =
        programme.yearlyTuitionInNaira == null ||
            programme.yearlyTuitionInNaira == 0.0 ||
            programme.yearlyTuitionInDollar == null ||
            programme.yearlyTuitionInDollar == 0.0

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .clip(RoundedCornerShape(Theme.spacing.x4))
                .clickable {
                    onSchoolProgrammeClick(programme)
                },
        shape = RoundedCornerShape(Theme.spacing.x4),
        colors =
            CardDefaults.cardColors(
                containerColor = MivaColors.White,
            ),
    ) {
        BoxWithConstraints {
            val maxWidth = this.maxWidth
            val phoneWidth = maxWidth < Constants.phoneWidth

            val imageScale =
                when {
                    maxWidth < 700.dp -> ContentScale.Crop
                    else -> ContentScale.Crop
                }

            Column {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.6f),
                ) {
                    val painter =
                        rememberAsyncImagePainter(
                            model = programme.image,
                            error = painterResource(R.drawable.place_holder_image),
                            fallback = painterResource(R.drawable.place_holder_image),
                        )
                    Image(
                        painter = painter,
                        contentDescription = stringResource(DesignSystemR.string.program_image),
                        contentScale = imageScale,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(topStart = Theme.spacing.x4, topEnd = Theme.spacing.x4))
                                .wrapContentHeight(),
                    )

                    val logo = postGradsProgrammeLogoMap[programme.name]

                    logo?.let {
                        Image(
                            painter = painterResource(id = logo),
                            contentDescription = stringResource(DesignSystemR.string.miva_logo),
                            modifier =
                                Modifier
                                    .size(Theme.spacing.x16)
                                    .align(Alignment.BottomEnd)
                                    .offset(x = (-16).dp, y = if (phoneWidth) Theme.spacing.x7 else Theme.spacing.x5)
                                    .padding(Theme.spacing.x2),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Theme.spacing.x2))

                Column(modifier = Modifier.padding(Theme.spacing.x4)) {
                    Text(
                        text = programme.name,
                        style =
                            Theme.typography.h1.copy(
                                fontSize = 20.sp,
                                color = MivaColors.TextColors.BlueDeep,
                            ),
                        textAlign = TextAlign.Start,
                    )

                    val yearlyTuition =
                        if (countryCode == DEFAULT_COUNTRY_CODE) {
                            "₦${
                                formatAmount(
                                    programme.yearlyTuitionInNaira ?: 0.0,
                                )
                            }"
                        } else {
                            "$${
                                formatAmount(
                                    programme.yearlyTuitionInDollar ?: 0.0,
                                )
                            }"
                        }

                    val tuitionPerSemester =
                        if (countryCode == DEFAULT_COUNTRY_CODE) {
                            "₦${
                                formatAmount(
                                    programme.tuitionFeePerSemesterInNaira ?: 0.0,
                                )
                            }"
                        } else {
                            "$${
                                formatAmount(
                                    programme.tuitionFeePerSemesterInDollar ?: 0.0,
                                )
                            }"
                        }
                    if (!isYearlyTuitionZeroOrNull) {
                        Spacer(modifier = Modifier.height(Theme.spacing.x3))
                        Text(
                            text = stringResource(DesignSystemR.string.full_tuition).uppercase(),
                            style =
                                Theme.typography.h1.copy(
                                    fontSize = 12.sp,
                                    color = MivaColors.TextColors.Blue300,
                                    fontWeight = FontWeight.W800,
                                ),
                        )
                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = yearlyTuition,
                            style =
                                Theme.typography.bodyM.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = MivaColors.TextColors.Red500,
                                ),
                        )
                    }

                    Spacer(modifier = Modifier.width(Theme.spacing.x4))
                }
            }
            Spacer(modifier = Modifier.height(Theme.spacing.x5))
        }
    }
}

@Preview(
    name = "Landscape",
    group = "BoxWithConstraints",
    widthDp = 1248,
    heightDp = 800,
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
    widthDp = 393,
    heightDp = 720,
)
@Preview(
    name = "Portrait350",
    group = "BoxWithConstraints",
    widthDp = 300,
    heightDp = 640,
)
@Preview
@Composable
fun PreviewProgrammeCard() {
}
