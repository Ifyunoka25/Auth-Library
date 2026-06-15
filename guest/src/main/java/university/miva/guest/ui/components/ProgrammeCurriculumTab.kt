package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import university.miva.designsystem.theme.MivaColors
import university.miva.guest.ui.viewmodel.models.CurriculumLevelInfo
import university.miva.guest.ui.viewmodel.models.ProgrammeInfo

@Composable
fun ProgrammeCurriculumTab(
    title: String,
    curriculums: List<CurriculumLevelInfo>,
    programmeInfo: ProgrammeInfo,
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
        programmeInfo.curriculumDescription?.let { description ->
            Text(
                text = description,
                style =
                    Theme.typography.bodyM.copy(
                        color = MivaColors.TextColors.BlueDeep.copy(alpha = 0.8f),
                        lineHeight = 28.sp,
                    ),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x8))
        }
        CurriculumSection(levels = curriculums, programmeInfo = programmeInfo)
        Spacer(modifier = Modifier.height(Theme.spacing.x5))
        ApplyNowButton(onClick = onClick)
    }
}
