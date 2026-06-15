package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.theme.MivaColors
import university.miva.guest.ui.viewmodel.models.ProgrammeRequirementsInfo
import university.miva.guest.ui.viewmodel.models.RequirementsInfo
import university.miva.designsystem.R as DesignSystemR

@Composable
fun ProgrammeRequirementTab(
    title: String,
    programmeRequirements: ProgrammeRequirementsInfo,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .padding(horizontal = Theme.spacing.x4, vertical = Theme.spacing.x6),
    ) {
        Spacer(modifier = Modifier.height(Theme.spacing.x2))
        Text(
            text = title,
            style =
                Theme.typography.h1.copy(
                    fontSize = 24.sp,
                ),
        )
        Spacer(modifier = Modifier.height(Theme.spacing.x2))
        Text(
            text = programmeRequirements.description,
            style =
                Theme.typography.bodyM.copy(
                    color = MivaColors.TextColors.BlueDeep.copy(alpha = 0.8f),
                    lineHeight = 28.sp,
                ),
        )
        Spacer(modifier = Modifier.height(Theme.spacing.x8))
        RequirementSection(requirements = programmeRequirements.requirementsInfo)
        Spacer(modifier = Modifier.height(Theme.spacing.x5))
        ApplyNowButton(onClick = onClick)
    }
}

@Composable
fun RequirementSection(requirements: List<RequirementsInfo>) {
    var expandedLevel by remember { mutableIntStateOf(-1) }

    Column(modifier = Modifier.background(Color(0xFFF2F5F8))) {
        requirements.forEachIndexed { index, requirementsInfo ->
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = Theme.spacing.x2),
            ) {
                Box(modifier = Modifier.background(MivaColors.Secondary.Blue100)) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 1.dp,
                                    top = 1.dp,
                                    end = 1.dp,
                                    bottom =
                                        if (expandedLevel !=
                                            index
                                        ) {
                                            1.dp
                                        } else {
                                            0.dp
                                        },
                                ).background(MivaColors.White)
                                .clickable { expandedLevel = if (expandedLevel == index) -1 else index }
                                .padding(horizontal = Theme.spacing.x4, vertical = Theme.spacing.x5),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                    ) {
                        Text(
                            text = requirementsInfo.title,
                            modifier = Modifier.weight(1f),
                            style =
                                Theme.typography.h1.copy(
                                    color = MivaColors.TextColors.BlueDeep,
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
                }

                if (expandedLevel == index) {
                    Box(modifier = Modifier.background(MivaColors.Secondary.Blue100)) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 1.dp, bottom = 1.dp, end = 1.dp)
                                    .background(MivaColors.White)
                                    .padding(horizontal = Theme.spacing.x4, vertical = Theme.spacing.x3),
                        ) {
                            if (requirementsInfo.subTitle.isNotEmpty()) {
                                Text(
                                    text = requirementsInfo.subTitle,
                                    style =
                                        Theme.typography.h1.copy(
                                            color = MivaColors.TextColors.BlueDeep,
                                            fontSize = 16.sp,
                                            lineHeight = 28.sp,
                                        ),
                                )
                                Spacer(modifier = Modifier.height(Theme.spacing.x3))
                            }
                            if (requirementsInfo.informationIntro.isNotEmpty()) {
                                Text(
                                    text = requirementsInfo.informationIntro,
                                    style =
                                        Theme.typography.bodyM.copy(
                                            color = MivaColors.TextColors.Blue400,
                                            lineHeight = 28.sp,
                                        ),
                                )
                                Spacer(modifier = Modifier.height(Theme.spacing.x6))
                                requirementsInfo.informationBulletPoints.forEach {
                                    BulletPoints(it)
                                }
                                Spacer(modifier = Modifier.height(Theme.spacing.x4))
                                Text(
                                    text = requirementsInfo.informationEnd,
                                    style =
                                        Theme.typography.bodyM.copy(
                                            color = MivaColors.TextColors.Blue400,
                                            lineHeight = 28.sp,
                                        ),
                                )
                            }

                            requirementsInfo.directEntryBulletPoints.forEach {
                                BulletPoints(it)
                            }
                            if (requirementsInfo.otherInformation?.isNotEmpty() == true) {
                                Text(
                                    text = requirementsInfo.otherInformation,
                                    style =
                                        Theme.typography.bodyM.copy(
                                            color = MivaColors.TextColors.Blue400,
                                        ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BulletPoints(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(start = Theme.spacing.x2),
    ) {
        Text(
            text = stringResource(DesignSystemR.string.dot),
            style =
                Theme.typography.bodyM.copy(
                    color = MivaColors.TextColors.BlueDeep,
                ),
        )
        Spacer(modifier = Modifier.width(Theme.spacing.x1))
        Text(
            text = text,
            style =
                Theme.typography.bodyM.copy(
                    color = MivaColors.TextColors.Blue400,
                    lineHeight = 28.sp,
                ),
        )
    }
    Spacer(modifier = Modifier.height(Theme.spacing.x2))
}
