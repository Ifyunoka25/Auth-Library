package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.DEFAULT_COUNTRY_CODE
import university.miva.designsystem.util.formatAmount
import university.miva.guest.ui.utils.isPostGraduates
import university.miva.guest.ui.viewmodel.Notice
import university.miva.guest.ui.viewmodel.models.ProgrammeInfo
import university.miva.designsystem.R as DesignSystemR

@Composable
fun ProgrammeDetailTab(
    title: String,
    countryCode: String,
    notice: Notice? = null,
    programme: ProgrammeInfo,
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
        programme.description?.let { description ->
            Spacer(modifier = Modifier.height(Theme.spacing.x2))
            Text(
                text = description,
                style =
                    Theme.typography.bodyM.copy(
                        color = MivaColors.TextColors.BlueDeep.copy(alpha = 0.8f),
                        lineHeight = 28.sp,
                    ),
            )
        }
        Spacer(modifier = Modifier.height(Theme.spacing.x8))
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = MivaColors.Secondary.Blue100,
                    ).background(color = Color.White)
                    .padding(horizontal = Theme.spacing.x4, vertical = Theme.spacing.x6),
        ) {
            Text(
                text = stringResource(DesignSystemR.string.course_summary),
                style =
                    Theme.typography.h1.copy(
                        fontSize = 20.sp,
                    ),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x5))
            HorizontalDivider(color = MivaColors.Secondary.Blue100)
            Spacer(modifier = Modifier.height(Theme.spacing.x5))
            CourseSummary(
                courseTitle = title,
                countryCode = countryCode,
                programme = programme,
                onClick = onClick,
            )
        }
        Spacer(modifier = Modifier.height(Theme.spacing.x6))
        if (notice != null) {
            Column(
                modifier =
                    Modifier
                        .background(color = MivaColors.Secondary.CanceledLight)
                        .padding(horizontal = Theme.spacing.x6, vertical = Theme.spacing.x4),
            ) {
                Text(
                    text = notice.title,
                    style =
                        Theme.typography.h1.copy(
                            fontSize = 14.sp,
                        ),
                )
                Spacer(modifier = Modifier.height(Theme.spacing.x2))
                Text(
                    text = notice.body,
                    style =
                        Theme.typography.bodyM.copy(
                            fontSize = 14.sp,
                            color = MivaColors.TextColors.BlueDeep,
                        ),
                )
            }
        }
        Spacer(modifier = Modifier.height(Theme.spacing.x5))
    }
}

@Composable
fun CourseSummary(
    courseTitle: String,
    countryCode: String,
    programme: ProgrammeInfo,
    onClick: () -> Unit,
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

    val isPostGraduates = programme.isPostGraduates()
    Column {
        StudyText(
            title = stringResource(DesignSystemR.string.study_level).uppercase(),
            description = courseTitle,
        )
        Spacer(modifier = Modifier.height(Theme.spacing.x6))
        programme.duration?.let {
            val monthsText = stringResource(DesignSystemR.string.months)
            val monthText = stringResource(DesignSystemR.string.month)
            val yearText = stringResource(DesignSystemR.string.year)
            val yearsText = stringResource(DesignSystemR.string.years)
            StudyText(
                title = stringResource(DesignSystemR.string.study_duration).uppercase(),
                description =
                    if (isPostGraduates) {
                        "$it ${if (it == 1) monthText else monthsText}"
                    } else {
                        "$it ${if (it == 1) yearText else yearsText}"
                    },
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x6))
        }
        programme.modeOfStudy?.let { modeOfStudy ->
            StudyText(
                title = stringResource(DesignSystemR.string.mode_of_study).uppercase(),
                description = modeOfStudy,
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x6))
        }

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
            StudyText(
                title = stringResource(DesignSystemR.string.tuition_per_session).uppercase(),
                description = yearlyTuition,
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x6))
        }

        if (!isFeePerSemesterZeroOrNull) {
            StudyText(
                title = stringResource(DesignSystemR.string.tuition_per_semester).uppercase(),
                description = tuitionPerSemester,
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x6))
        }
        ApplyNowButton(onClick = onClick)
    }
}

@Composable
fun StudyText(
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            style =
                Theme.typography.h1.copy(
                    fontSize = 12.sp,
                    letterSpacing = 1.8.sp,
                    color = MivaColors.Secondary.Blue50,
                ),
        )
        Spacer(modifier = Modifier.height(Theme.spacing.x1))
        Text(
            text = description,
            style =
                Theme.typography.h1.copy(
                    fontSize = 16.sp,
                ),
        )
    }
}

@Composable
fun TuitionText(
    title: String,
    countryCode: String,
    oldAmount: String?,
    newAmount: String?,
    savedAmount: String,
) {
    val isZeroOrNull =
        savedAmount.isBlank() ||
            savedAmount == "0" ||
            savedAmount == "0.00"
    val oldAmountUi =
        if (countryCode == DEFAULT_COUNTRY_CODE) {
            "₦$oldAmount"
        } else {
            "$$oldAmount"
        }
    val newAmountUi =
        if (countryCode == DEFAULT_COUNTRY_CODE) {
            "₦$newAmount"
        } else {
            "$$newAmount"
        }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = title,
            style =
                Theme.typography.h1.copy(
                    fontSize = 12.sp,
                    letterSpacing = 1.8.sp,
                    color = MivaColors.Secondary.Blue50,
                ),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = oldAmountUi,
                style =
                    Theme.typography.headingM.copy(
                        color = MivaColors.Secondary.Blue450,
                    ),
            )
        }
    }
}

@Composable
fun SavePriceBadge(
    amount: String,
    countryCode: String,
) {
    val newAmountUi =
        if (countryCode == DEFAULT_COUNTRY_CODE) {
            "₦$amount"
        } else {
            "$$amount"
        }
    Row {
        Text(
            text = newAmountUi,
            modifier =
                Modifier
                    .background(color = MivaColors.Secondary.Green500, shape = RoundedCornerShape(Theme.spacing.x2))
                    .padding(horizontal = Theme.spacing.x3, vertical = 6.dp),
            style =
                Theme.typography.body.copy(
                    color = MivaColors.TextColors.White,
                    fontSize = 12.sp,
                ),
        )
    }
}
