package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.theme.MivaColors
import university.miva.guest.ui.utils.getSemesterOrder
import university.miva.guest.ui.utils.isPostGraduates
import university.miva.guest.ui.viewmodel.models.CurriculumLevelInfo
import university.miva.guest.ui.viewmodel.models.ProgrammeInfo
import university.miva.designsystem.R as DesignSystemR

@Composable
fun CurriculumSection(
    levels: List<CurriculumLevelInfo>,
    programmeInfo: ProgrammeInfo,
) {
    var expandedLevel by remember { mutableIntStateOf(-1) }

    /*
     * Sort levels based on the numeric part of the level name
     * E.g 100 Level, 200 Level, 300 Level, 400 Level
     */
    val sortedLevels =
        levels.sortedBy {
            it.level.substringBefore('_').toIntOrNull() ?: 0
        }

    if (programmeInfo.isPostGraduates()) {
        // Postgraduate UI (semester as card title)
        levels
            .flatMap { it.semesters } // we don’t care about levels here
            .sortedBy { getSemesterOrder(it.semester) }
            .forEachIndexed { index, semester ->
                Column(modifier = Modifier.padding(bottom = Theme.spacing.x2)) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .border(1.dp, MivaColors.Secondary.Blue100)
                                .background(Color.White)
                                .clickable { expandedLevel = if (expandedLevel == index) -1 else index }
                                .padding(horizontal = Theme.spacing.x4, vertical = Theme.spacing.x5),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = semester.semester.replace("_", " "),
                            style = Theme.typography.h1.copy(fontSize = 20.sp),
                        )
                        Image(
                            painter =
                                painterResource(
                                    id =
                                        if (expandedLevel == index) {
                                            DesignSystemR.drawable.ic_arrow_up
                                        } else {
                                            DesignSystemR.drawable.ic_arrow_down
                                        },
                                ),
                            contentDescription = stringResource(DesignSystemR.string.arrow_down),
                        )
                    }

                    if (expandedLevel == index) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, MivaColors.Secondary.Blue100)
                                    .background(MivaColors.Secondary.Grey50)
                                    .padding(horizontal = Theme.spacing.x4, vertical = Theme.spacing.x3),
                        ) {
                            // Header row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    text = semester.semester.replace("_", " "),
                                    style =
                                        Theme.typography.h4.copy(
                                            color = MivaColors.TextColors.BlueDeep,
                                            fontWeight = FontWeight.Bold,
                                        ),
                                )
                                Text(
                                    text = stringResource(DesignSystemR.string.status),
                                    style =
                                        Theme.typography.h1.copy(
                                            color = MivaColors.TextColors.BlueDeep,
                                            fontSize = 16.sp,
                                        ),
                                )
                            }
                            Spacer(modifier = Modifier.height(Theme.spacing.x4))
                            HorizontalDivider(color = MivaColors.Secondary.Blue40)
                            Spacer(modifier = Modifier.height(Theme.spacing.x4))

                            // List of courses
                            semester.courses.forEach { course ->
                                if (!course.courseName.isNullOrEmpty()) {
                                    Row(
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = Theme.spacing.x3),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(
                                            text = course.courseName.lowercase(),
                                            style =
                                                Theme.typography.bodyM.copy(
                                                    color = MivaColors.TextColors.BlueDeep,
                                                ),
                                        )
                                        Text(
                                            text = course.courseType?.take(1).orEmpty(), // C or E
                                            style =
                                                Theme.typography.headingM.copy(
                                                    color = MivaColors.TextColors.BlueDeep,
                                                ),
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(Theme.spacing.x4))
                            HorizontalDivider(color = MivaColors.Secondary.Blue40)
                            Spacer(modifier = Modifier.height(Theme.spacing.x2))

                            // Legend
                            Column(verticalArrangement = Arrangement.spacedBy(Theme.spacing.x1)) {
                                Text(
                                    text = stringResource(DesignSystemR.string.c_core),
                                    style =
                                        Theme.typography.bodyM.copy(
                                            color = MivaColors.TextColors.BlueDeep,
                                        ),
                                )
                                Text(
                                    text = stringResource(DesignSystemR.string.e_elective),
                                    style =
                                        Theme.typography.bodyM.copy(
                                            color = MivaColors.TextColors.BlueDeep,
                                        ),
                                )
                            }
                        }
                    }
                }
            }
    } else {
        Column(modifier = Modifier.background(MivaColors.Secondary.Grey50)) {
            sortedLevels.forEachIndexed { index, levelInfo ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = Theme.spacing.x2),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 1.dp,
                                    color = MivaColors.Secondary.Blue100,
                                ).background(Color.White)
                                .clickable { expandedLevel = if (expandedLevel == index) -1 else index }
                                .padding(horizontal = Theme.spacing.x4, vertical = Theme.spacing.x5),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                    ) {
                        Text(
                            text = levelInfo.level.replace("_", " "),
                            modifier = Modifier.weight(1f),
                            style =
                                Theme.typography.h1.copy(
                                    fontSize = 20.sp,
                                ),
                        )
                        Spacer(modifier = Modifier.width(Theme.spacing.x5))
                        Image(
                            painter =
                                painterResource(
                                    id =
                                        if (expandedLevel ==
                                            index
                                        ) {
                                            DesignSystemR.drawable.ic_arrow_up
                                        } else {
                                            DesignSystemR.drawable.ic_arrow_down
                                        },
                                ),
                            contentDescription = stringResource(DesignSystemR.string.arrow_down),
                        )
                    }

                    if (expandedLevel == index) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = MivaColors.Secondary.Blue100,
                                    ).background(MivaColors.Secondary.Grey50)
                                    .padding(horizontal = Theme.spacing.x4, vertical = Theme.spacing.x3),
                        ) {
                            /*
                             * Sort semesters based on levels before displaying
                             * Example FIRST SEMESTER, SECOND SEMESTER, THIRD SEMESTER, etc.
                             */
                            val sortedSemesters = levelInfo.semesters.sortedBy { getSemesterOrder(it.semester) }
                            sortedSemesters.forEach { semester ->
                                val firstCourseUnit = semester.courses.firstOrNull()?.creditUnit
                                val isNumber = firstCourseUnit != null
                                Spacer(modifier = Modifier.height(Theme.spacing.x2))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = semester.semester.replace("_", " "),
                                        style =
                                            Theme.typography.h4.copy(
                                                color = MivaColors.TextColors.BlueDeep,
                                                fontWeight = FontWeight.Bold,
                                            ),
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text =
                                            if (isNumber) {
                                                stringResource(DesignSystemR.string.units)
                                            } else {
                                                stringResource(DesignSystemR.string.status)
                                            },
                                        style =
                                            Theme.typography.h1.copy(
                                                color = MivaColors.TextColors.BlueDeep,
                                                fontSize = 16.sp,
                                            ),
                                    )
                                }
                                Spacer(modifier = Modifier.height(Theme.spacing.x6))
                                HorizontalDivider(color = MivaColors.Secondary.Blue40)
                                Spacer(modifier = Modifier.height(Theme.spacing.x4))

                                semester.courses.forEach { course ->
                                    if (course.courseName?.isNotEmpty() == true) {
                                        Row(
                                            modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = Theme.spacing.x3),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Text(
                                                text = course.courseName,
                                                modifier = Modifier.weight(1f),
                                                style =
                                                    Theme.typography.bodyM.copy(
                                                        color = MivaColors.TextColors.BlueDeep,
                                                    ),
                                            )
                                            Spacer(modifier = Modifier.width(Theme.spacing.x5))
                                            Text(
                                                text =
                                                    if (programmeInfo.isPostGraduates()) {
                                                        course.courseType
                                                            .orEmpty()
                                                            .substring(0, 1)
                                                    } else {
                                                        course.creditUnit.toString()
                                                    },
                                                style =
                                                    Theme.typography.headingM.copy(
                                                        color = MivaColors.TextColors.BlueDeep,
                                                    ),
                                            )
                                        }
                                    }
                                }

                                if (programmeInfo.isPostGraduates() &&
                                    semester.courses.any { it.courseType?.isNotEmpty() == true }
                                ) {
                                    Spacer(modifier = Modifier.height(Theme.spacing.x4))
                                    HorizontalDivider(color = MivaColors.Secondary.Blue40)
                                    Spacer(modifier = Modifier.height(Theme.spacing.x4))

                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(Theme.spacing.x1),
                                    ) {
                                        Text(
                                            text = stringResource(DesignSystemR.string.c_core),
                                            style =
                                                Theme.typography.bodyM.copy(
                                                    color = MivaColors.TextColors.BlueDeep,
                                                ),
                                        )
                                        Text(
                                            text = stringResource(DesignSystemR.string.e_elective),
                                            style =
                                                Theme.typography.bodyM.copy(
                                                    color = MivaColors.TextColors.BlueDeep,
                                                ),
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(Theme.spacing.x4))
                            }
                        }
                    }
                }
            }
        }
    }
}
