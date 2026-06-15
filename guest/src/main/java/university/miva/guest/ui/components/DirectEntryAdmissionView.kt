package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.components.button.AppButton
import university.miva.designsystem.components.image.ClickableSpanImage
import university.miva.designsystem.components.sheets.AppBottomSheet
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.R as DesignSystemR

@Composable
fun DirectEntryAdmissionView(
    visible: Boolean,
    onDismiss: () -> Unit,
    onApplyClick: () -> Unit,
) {
    val eligibilityItems =
        listOf(
            stringResource(DesignSystemR.string.passes_in_relevant_subjects),
            stringResource(DesignSystemR.string.diploma_in_relevant_areas),
            stringResource(DesignSystemR.string.profession_certificates_in),
        )

    AppBottomSheet(
        visible = visible,
        onFinalDismiss = onDismiss,
        dragHandle = null,
        content = {
            Surface(
                shape = RoundedCornerShape(topStart = Theme.spacing.x4, topEnd = Theme.spacing.x4),
                color = Color.White,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
            ) {
                LazyColumn {
                    item {
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Theme.spacing.x4),
                            horizontalArrangement = Arrangement.Absolute.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = stringResource(DesignSystemR.string.direct_entry_admissions),
                                style =
                                    Theme.typography.h3.copy(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MivaColors.TextColors.BlueDeep,
                                    ),
                                textAlign = TextAlign.Start,
                            )
                            Spacer(modifier = Modifier.width(Theme.spacing.x6))
                            ClickableSpanImage(
                                image = R.drawable.ic_close,
                                contentDescription = stringResource(R.string.close),
                                tint = MivaColors.TextColors.BlueDeep,
                            ) {
                                onDismiss()
                            }
                        }
                        HorizontalDivider(color = MivaColors.Secondary.DividerColor)
                        Spacer(modifier = Modifier.height(Theme.spacing.x6))
                        Image(
                            painter = painterResource(id = DesignSystemR.drawable.ic_direct_entry_grad),
                            contentDescription = stringResource(DesignSystemR.string.direct_entry_grads),
                            modifier = Modifier.padding(horizontal = Theme.spacing.x4),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.height(Theme.spacing.x6))
                        Text(
                            text = stringResource(DesignSystemR.string.advance_your_education_and_land_better_),
                            modifier = Modifier.padding(horizontal = Theme.spacing.x4),
                            style =
                                Theme.typography.bodyM.copy(
                                    color = MivaColors.TextColors.Blue400,
                                    lineHeight = 24.sp,
                                ),
                            textAlign = TextAlign.Start,
                        )

                        Spacer(modifier = Modifier.height(Theme.spacing.x6))
                        Card(
                            shape = RoundedCornerShape(Theme.spacing.x3),
                            colors =
                                CardDefaults.cardColors(
                                    containerColor = Color.Transparent,
                                ),
                            border = BorderStroke(1.dp, MivaColors.Secondary.CardBorderColor),
                            modifier = Modifier.padding(horizontal = Theme.spacing.x4),
                        ) {
                            Column(modifier = Modifier.padding(Theme.spacing.x5)) {
                                Text(
                                    text = stringResource(DesignSystemR.string.to_be_eligible_for_direct_entry_program),
                                    style =
                                        Theme.typography.h4.copy(
                                            fontWeight = FontWeight.Bold,
                                            lineHeight = 32.sp,
                                        ),
                                )
                                Spacer(modifier = Modifier.height(Theme.spacing.x4))

                                eligibilityItems.forEach {
                                    Row(
                                        modifier = Modifier.padding(bottom = 10.dp),
                                        verticalAlignment = Alignment.Top,
                                    ) {
                                        Box(
                                            modifier =
                                                Modifier
                                                    .padding(top = Theme.spacing.x2)
                                                    .size(Theme.spacing.x2)
                                                    .background(MivaColors.TextColors.Red500, shape = CircleShape),
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = it,
                                            style =
                                                Theme.typography.bodyM.copy(
                                                    color = MivaColors.TextColors.BlueDeep,
                                                    lineHeight = 28.sp,
                                                ),
                                            modifier = Modifier.weight(1f),
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = stringResource(DesignSystemR.string.for_detailed_info_on_each_programs_),
                                    style =
                                        Theme.typography.bodyM.copy(
                                            lineHeight = 28.sp,
                                        ),
                                )

                                Spacer(modifier = Modifier.height(Theme.spacing.x4))

                                Text(
                                    text =
                                        stringResource(
                                            DesignSystemR.string.please_note_that_submission_of_jaam_board,
                                        ),
                                    style =
                                        Theme.typography.bodyM.copy(
                                            lineHeight = 28.sp,
                                        ),
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(Theme.spacing.x8))
                        AppButton(
                            title = stringResource(DesignSystemR.string.apply_for_direct_entry),
                            onClick = onApplyClick,
                        )

                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }
        },
    )
}
