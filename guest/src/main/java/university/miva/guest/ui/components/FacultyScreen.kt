import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import university.miva.designsystem.R
import university.miva.designsystem.components.button.AppBackButton
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.Routes
import university.miva.designsystem.util.encodeToJson
import university.miva.designsystem.util.encodeToUrl
import university.miva.designsystem.util.viewAllClickable
import university.miva.guest.ui.components.AboutFacultyScreen
import university.miva.guest.ui.viewmodel.models.FacultyInfo
import university.miva.guest.ui.viewmodel.models.ProgrammeInfo
import university.miva.designsystem.R as DesignSystemR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultyScreen(
    programmes: List<ProgrammeInfo>,
    faculty: FacultyInfo,
    navController: NavController,
) {
    var showAboutFacultyScreen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        containerColor = MivaColors.Secondary.Grey50,
        topBar = {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp, bottom = Theme.spacing.x10),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AppBackButton(
                    onClick = {
                        navController.popBackStack()
                    },
                )
                Spacer(
                    modifier =
                        Modifier
                            .width(Theme.spacing.x2)
                            .weight(1f),
                )
                if (faculty.description.isNullOrEmpty().not()) {
                    Row(
                        modifier =
                            Modifier
                                .viewAllClickable {
                                    showAboutFacultyScreen = true
                                },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = DesignSystemR.drawable.ic_info),
                            contentDescription = stringResource(R.string.info_icon),
                        )
                        Spacer(modifier = Modifier.width(Theme.spacing.x1))
                        Text(
                            text = stringResource(DesignSystemR.string.about),
                            style =
                                Theme.typography.bodyM.copy(
                                    color = MivaColors.TextColors.BlueDeep,
                                ),
                            textAlign = TextAlign.Start,
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.width(Theme.spacing.x4),
                )
            }
        },
    ) { paddingValues ->

        BoxWithConstraints {
            val maxWidth = this.maxWidth

            val imagePadding =
                when {
                    maxWidth < 500.dp -> Theme.spacing.x2
                    else -> Theme.spacing.x6
                }

            val imageHeight =
                when {
                    maxWidth < 600.dp -> 204.dp
                    else -> 400.dp
                }

            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = paddingValues.calculateTopPadding() / 2)
                        .systemBarsPadding(),
            ) {
                item {
                    if (showAboutFacultyScreen) {
                        ModalBottomSheet(
                            onDismissRequest = { showAboutFacultyScreen = false },
                            sheetState = sheetState,
                            containerColor = Color.Transparent,
                        ) {
                            AboutFacultyScreen(
                                onDismiss = { showAboutFacultyScreen = false },
                                faculty = faculty,
                            )
                        }
                    }
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Theme.spacing.x4),
                    ) {
                        Spacer(modifier = Modifier.height(imagePadding))
                        val painter = rememberAsyncImagePainter(model = faculty.pageImage)
                        Image(
                            painter = painter,
                            contentDescription = stringResource(R.string.info_icon),
                            modifier = Modifier.fillMaxWidth().heightIn(min = 204.dp, max = imageHeight),
                        )
                    }
                    val multipliedList =
                        (1..10)
                            .flatMap {
                                programmes
                            }.shuffled()

                    BscProgrammesList(
                        schoolProgrammes = programmes,
                        onProgrammeClick = { selectedSchoolProgramme ->
                            val programmeJson = selectedSchoolProgramme.encodeToJson().encodeToUrl()
                            navController.navigate("${Routes.FACULTY_DETAILS}/$programmeJson")
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun BscProgrammesList(
    schoolProgrammes: List<ProgrammeInfo>,
    onProgrammeClick: (ProgrammeInfo) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(Theme.spacing.x4)
                .background(color = Color.White),
    ) {
        schoolProgrammes.forEach { schoolProgramme ->
            ProgrammeListItem(
                title = schoolProgramme.name,
                onClick = { onProgrammeClick(schoolProgramme) },
            )
        }
    }
}

@Composable
fun ProgrammeListItem(
    title: String,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .clickable { onClick() },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Theme.spacing.x3, vertical = 21.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                style = Theme.typography.bodyM,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(Theme.spacing.x3))
            Image(
                painter = painterResource(id = DesignSystemR.drawable.ic_right_arrow),
                contentDescription = stringResource(DesignSystemR.string.right_arrow),
            )
        }
    }

    HorizontalDivider(
        color = Color(0xFFE5EAF2),
        thickness = 1.dp,
    )
}
