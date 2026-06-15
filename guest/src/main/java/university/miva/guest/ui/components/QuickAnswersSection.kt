package university.miva.guest.ui.components

import Theme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.Constants
import university.miva.designsystem.R as DesignSystemR

@Composable
fun QuickAnswerSection() {
    var faqItems by remember {
        mutableStateOf(
            Constants.faqs.map { (question, answer) ->
                FaqItem(question = question, answer = answer)
            },
        )
    }

    BoxWithConstraints {
        val bWCScope = this
        val maxWidth = bWCScope.maxWidth
        val phoneWidth = maxWidth < Constants.phoneWidth

        Column(
            modifier =
                Modifier
                    .background(
                        MivaColors.Secondary.FAQColor,
                    ).padding(
                        top = Theme.spacing.x3,
                    ),
        ) {
            Spacer(modifier = Modifier.height(Theme.spacing.x7))
            Text(
                stringResource(DesignSystemR.string.everything_you_need_to_know_about_miva),
                modifier = Modifier.padding(horizontal = Theme.spacing.x4),
                style =
                    Theme.typography.h3.copy(
                        fontWeight = FontWeight.Bold,
                        color = MivaColors.TextColors.BlueDeep,
                    ),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.x6))

            Text(
                stringResource(DesignSystemR.string.quick_answers_to_common_inquiries),
                modifier = Modifier.padding(horizontal = Theme.spacing.x4),
                style =
                    Theme.typography.h3.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = MivaColors.TextColors.BlueDeep,
                    ),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.x3))

            faqItems.forEachIndexed { index, item ->
                FaqItemView(
                    item = item,
                    onToggle = {
                        faqItems =
                            faqItems.mapIndexed { i, faq ->
                                if (i == index) {
                                    faq.copy(isExpanded = !faq.isExpanded)
                                } else {
                                    faq.copy(isExpanded = false)
                                }
                            }
                    },
                )

                if (index < faqItems.lastIndex) {
                    HorizontalDivider(color = MivaColors.Secondary.DividerColor)
                }
            }
        }
    }
}

@Composable
fun FaqItemView(
    item: FaqItem,
    onToggle: () -> Unit,
) {
    BoxWithConstraints {
        val bWCScope = this
        val maxWidth = bWCScope.maxWidth
        val phoneWidth = maxWidth < Constants.phoneWidth

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() }
                    .padding(
                        vertical = Theme.spacing.x2,
                        horizontal = if (phoneWidth) Theme.spacing.x3 else Theme.spacing.x6,
                    ),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = Theme.spacing.x3),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = item.question,
                    style =
                        Theme.typography.h3.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MivaColors.TextColors.BlueDeep,
                        ),
                    modifier = Modifier.weight(1f),
                )

                Spacer(modifier = Modifier.width(Theme.spacing.x3))

                Image(
                    painter =
                        if (item.isExpanded) {
                            painterResource(DesignSystemR.drawable.ic_fab_close)
                        } else {
                            painterResource(DesignSystemR.drawable.ic_fab_open)
                        },
                    contentDescription = if (item.isExpanded) "Collapse" else "Expand",
                    modifier = Modifier.size(Theme.spacing.x6),
                )
            }

            AnimatedVisibility(
                visible = item.isExpanded,
            ) {
                Spacer(modifier = Modifier.height(Theme.spacing.x2))
                Text(
                    text = item.answer,
                    style =
                        Theme.typography.h3.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = MivaColors.TextColors.Gray,
                        ),
                )
            }
        }
    }
}

data class FaqItem(
    val question: String,
    val answer: String,
    val isExpanded: Boolean = false,
)
