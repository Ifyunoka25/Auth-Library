package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.DEFAULT_COUNTRY_CODE
import university.miva.designsystem.util.formatAmount
import university.miva.designsystem.util.toTitleCase
import university.miva.guest.ui.utils.isPostGraduates
import university.miva.guest.ui.viewmodel.models.ProgrammeInfo
import university.miva.designsystem.R as DesignSystemR

@Composable
fun ProgrammeTuitionTab(
    title: String,
    body: String,
    countryCode: String,
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
        Spacer(modifier = Modifier.height(Theme.spacing.x2))
        Text(
            text = body,
            style =
                Theme.typography.bodyM.copy(
                    color = MivaColors.TextColors.BlueDeep.copy(alpha = 0.8f),
                    lineHeight = 28.sp,
                ),
        )
        Spacer(modifier = Modifier.height(Theme.spacing.x8))
        val isZeroOrNull =
            programme.tuitionFeePerSemesterInNaira == null ||
                programme.tuitionFeePerSemesterInNaira == 0.0 ||
                programme.tuitionFeePerSemesterInDollar == null ||
                programme.tuitionFeePerSemesterInDollar == 0.0

        val isYearlyTuitionZeroOrNull =
            programme.yearlyTuitionInNaira == null ||
                programme.yearlyTuitionInNaira == 0.0 ||
                programme.yearlyTuitionInDollar == null ||
                programme.yearlyTuitionInDollar == 0.0

        if (!isZeroOrNull) {
            TuitionPerSemesterSection(
                programmeDetails = programme,
                countryCode = countryCode,
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x2))
        }

        if (!isYearlyTuitionZeroOrNull) {
            TuitionPerSessionSection(
                programmeDetails = programme,
                countryCode = countryCode,
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x5))
        }

        if (programme.isPostGraduates()) {
            TuitionPerMonthSection()
            Spacer(modifier = Modifier.height(Theme.spacing.x5))
        }

        ApplyNowButton(onClick = onClick)
    }
}

@Composable
fun TuitionPerSemesterSection(
    programmeDetails: ProgrammeInfo,
    countryCode: String,
) {
    Column(
        modifier =
            Modifier
                .border(
                    width = 1.dp,
                    color = MivaColors.Secondary.Blue100,
                ).background(MivaColors.White)
                .padding(Theme.spacing.x6),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Theme.spacing.x2),
        ) {
            Text(
                text = stringResource(DesignSystemR.string.tuition_per_semester).toTitleCase(),
                style =
                    Theme.typography.h1.copy(
                        color = MivaColors.TextColors.BlueDeep,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                    ),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x4))
            Text(
                text = stringResource(DesignSystemR.string.pay_for_semester_no_hidden_charges),
                style =
                    Theme.typography.bodyM.copy(
                        color = MivaColors.TextColors.Blue400,
                    ),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x4))
            programmeDetails.tuitionFeePerSemesterInNaira?.let {
                val tuitionFeePerSemester =
                    if (countryCode == DEFAULT_COUNTRY_CODE) {
                        "₦${formatAmount(it)}"
                    } else {
                        "$${formatAmount(programmeDetails.tuitionFeePerSemesterInDollar ?: 0.0)}"
                    }
                Text(
                    text = tuitionFeePerSemester,
                    style =
                        Theme.typography.h1.copy(
                            color = MivaColors.TextColors.BlueDeep,
                            fontSize = 24.sp,
                        ),
                )
            }
        }
    }
}

@Composable
fun TuitionPerSessionSection(
    programmeDetails: ProgrammeInfo,
    countryCode: String,
) {
    val isZeroOrNull =
        if (countryCode == DEFAULT_COUNTRY_CODE) {
            programmeDetails.tuitionFeePerSemesterInNaira == null ||
                programmeDetails.tuitionFeePerSemesterInNaira == 0.0
        } else {
            programmeDetails.tuitionFeePerSemesterInDollar == null ||
                programmeDetails.tuitionFeePerSemesterInDollar == 0.0
        }
    val isYearlyTuitionZeroOrNull =
        if (countryCode == DEFAULT_COUNTRY_CODE) {
            programmeDetails.yearlyTuitionInNaira == null ||
                programmeDetails.yearlyTuitionInNaira == 0.0
        } else {
            programmeDetails.yearlyTuitionInDollar == null ||
                programmeDetails.yearlyTuitionInDollar == 0.0
        }

    val yearlyTuitionInNaira = programmeDetails.yearlyTuitionInNaira?.let { formatAmount(it) }
    val yearlyTuitionInDollar = programmeDetails.yearlyTuitionInDollar?.let { formatAmount(it) }
    val yearlyTuition =
        if (countryCode == DEFAULT_COUNTRY_CODE) {
            "₦$yearlyTuitionInNaira"
        } else {
            "$$yearlyTuitionInDollar"
        }

    Column(
        modifier =
            Modifier
                .border(
                    width = 1.dp,
                    color = MivaColors.Secondary.Blue100,
                ).background(MivaColors.White)
                .padding(Theme.spacing.x6),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Theme.spacing.x2),
        ) {
            Text(
                text = stringResource(DesignSystemR.string.tuition_per_session).toTitleCase(),
                style =
                    Theme.typography.h1.copy(
                        color = MivaColors.TextColors.BlueDeep,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                    ),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x4))
            Text(
                text = stringResource(DesignSystemR.string.pay_for_session_no_hidden_charges),
                style =
                    Theme.typography.bodyM.copy(
                        color = MivaColors.TextColors.Blue400,
                    ),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x4))
            if (!isYearlyTuitionZeroOrNull) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = yearlyTuition,
                        style =
                            Theme.typography.h1.copy(
                                fontSize = 24.sp,
                            ),
                    )
                }
            }
        }
    }
}

@Composable
fun TuitionPerMonthSection() {
    Column(
        modifier =
            Modifier
                .border(
                    width = 1.dp,
                    color = MivaColors.Secondary.Blue100,
                ).background(MivaColors.White)
                .padding(Theme.spacing.x6),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = Theme.spacing.x2),
        ) {
            Text(
                text = stringResource(DesignSystemR.string.tuition_per_month).toTitleCase(),
                style =
                    Theme.typography.h1.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                    ),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x4))
            Text(
                text = stringResource(DesignSystemR.string.spread_it_out_monthly_with_edubanc),
                style =
                    Theme.typography.bodyM.copy(
                        color = MivaColors.TextColors.Blue400,
                    ),
            )
        }
    }
}
