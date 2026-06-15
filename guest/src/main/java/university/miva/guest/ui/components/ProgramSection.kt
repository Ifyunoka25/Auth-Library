package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.DEFAULT_COUNTRY_CODE
import university.miva.designsystem.util.formatAmount
import university.miva.designsystem.util.viewAllClickable
import university.miva.guest.ui.viewmodel.models.ProgrammeInfo
import university.miva.designsystem.R as DesignSystemR

@Composable
fun ProgramSection(
    title: String,
    countryCode: String,
    programmes: List<ProgrammeInfo>,
    goToViewAll: (String) -> Unit,
    goToProgramDetails: (ProgrammeInfo) -> Unit,
) {
    Column {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Theme.spacing.x2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(Theme.spacing.x1))
            Text(
                title,
                style =
                    Theme.typography.bodyM.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MivaColors.TextColors.Blue500,
                    ),
            )
            Spacer(modifier = Modifier.weight(1f))
            if (programmes.isNotEmpty()) {
                Text(
                    stringResource(DesignSystemR.string.view_all),
                    modifier =
                        Modifier
                            .clip(shape = RoundedCornerShape(Theme.spacing.x5))
                            .viewAllClickable { goToViewAll(title) },
                    style =
                        Theme.typography.bodyM.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = MivaColors.TextColors.Blue500,
                        ),
                )
            }
        }

        Spacer(modifier = Modifier.height(Theme.spacing.x2))

        if (programmes.isEmpty()) {
            Card(
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(Theme.spacing.x2))
                        .padding(Theme.spacing.x4),
                shape = RoundedCornerShape(Theme.spacing.x2),
                colors =
                    CardDefaults.cardColors(
                        containerColor = MivaColors.Primary.White,
                    ),
                elevation = CardDefaults.cardElevation(),
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = Theme.spacing.x10),
                    contentAlignment = Alignment.Center,
                ) {
                    BasicText(
                        stringResource(university.miva.guest.R.string.no_matching_result),
                        style =
                            Theme.typography.bodyM.copy(
                                fontSize = 14.sp,
                                color = MivaColors.TextColors.Blue400,
                            ),
                    )
                }
            }
        } else {
            LazyRow(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = Theme.spacing.x3),
            ) {
                items(programmes) { programme ->
                    ProgrammeCard(programme, countryCode, goToProgramDetails)
                    Spacer(modifier = Modifier.width(Theme.spacing.x2))
                }
            }
        }
    }
}

@Composable
fun ProgrammeCard(
    programme: ProgrammeInfo,
    countryCode: String,
    goToProgramDetails: (ProgrammeInfo) -> Unit,
) {
    val isYearlyTuitionZeroOrNull =
        programme.yearlyTuitionInNaira == null ||
            programme.yearlyTuitionInNaira == 0.0 ||
            programme.yearlyTuitionInDollar == null ||
            programme.yearlyTuitionInDollar == 0.0

    Card(
        modifier =
            Modifier
                .width(280.dp)
                .padding(vertical = Theme.spacing.x2)
                .clip(RoundedCornerShape(Theme.spacing.x2))
                .clickable { goToProgramDetails(programme) },
        shape = RoundedCornerShape(Theme.spacing.x2),
        colors =
            CardDefaults.cardColors(
                containerColor = MivaColors.Primary.White,
            ),
        elevation = CardDefaults.cardElevation(),
    ) {
        Column {
            val painter =
                rememberAsyncImagePainter(
                    model = programme.image,
                    error = painterResource(R.drawable.place_holder_image),
                    fallback = painterResource(R.drawable.place_holder_image),
                )
            Image(
                painter = painter,
                contentDescription = stringResource(DesignSystemR.string.programme_image),
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp),
            )

            Column(
                modifier =
                    Modifier
                        .padding(Theme.spacing.x2),
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.height(Theme.spacing.x2))
                if (programme.facultyName.isNullOrEmpty().not()) {
                    Text(
                        programme.facultyName.orEmpty(),
                        style =
                            Theme.typography.bodyM.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MivaColors.TextColors.Brown600,
                            ),
                    )
                }
                if (programme.name.isEmpty().not()) {
                    Spacer(modifier = Modifier.height(Theme.spacing.x2))
                    Text(
                        programme.name,
                        style =
                            Theme.typography.bodyM.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MivaColors.TextColors.BlueDeep,
                            ),
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.x2))
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
                if (!isYearlyTuitionZeroOrNull) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
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
        }
    }
}
