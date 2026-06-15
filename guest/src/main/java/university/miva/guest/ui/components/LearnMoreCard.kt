package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.R as DesignSystemR

@Composable
fun LearnMoreCard(
    onClickDirectEntry: () -> Unit,
    onClickTransfers: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MivaColors.Secondary.Gray200),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(Theme.spacing.x8))
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(Theme.spacing.x4)
                    .background(
                        color = MivaColors.TextColors.BlueDeep,
                        shape = RoundedCornerShape(Theme.spacing.x2),
                    ).padding(Theme.spacing.x4),
        ) {
            Text(
                text = stringResource(DesignSystemR.string.learn_more_about),
                style =
                    Theme.typography.h3.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MivaColors.TextColors.White,
                    ),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.x4))

            HorizontalDivider(color = Color.White.copy(alpha = 0.2f))

            LearnMoreItem(
                label = stringResource(DesignSystemR.string.direct_entry_admissions),
                onClick = onClickDirectEntry,
            )

            HorizontalDivider(color = Color.White.copy(alpha = 0.2f))

            LearnMoreItem(
                label = stringResource(DesignSystemR.string.inter_university_transfers),
                onClick = onClickTransfers,
            )
        }
        Spacer(modifier = Modifier.height(Theme.spacing.x12))
    }
}

@Composable
fun LearnMoreItem(
    label: String,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = Theme.spacing.x4),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style =
                Theme.typography.h3.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = MivaColors.TextColors.White,
                ),
        )

        Image(
            painterResource(DesignSystemR.drawable.ic_arrow_right),
            contentDescription = "Go",
            modifier = Modifier.size(Theme.spacing.x4),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LearnMoreCardPreview() {
    LearnMoreCard(
        onClickDirectEntry = { /* navigate */ },
        onClickTransfers = { /* navigate */ },
    )
}
