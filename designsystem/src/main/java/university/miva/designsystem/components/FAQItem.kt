package university.miva.designsystem.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors

@Composable
fun FAQItem(
    question: String,
    answer: String,
    isExpanded: Boolean,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(0.dp),
        colors =
            CardDefaults.cardColors().copy(
                containerColor = MivaColors.White,
            ),
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp)
                .clickable { onClick() },
    ) {
        Column(modifier = Modifier.padding(Theme.spacing.x4)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = question,
                    style =
                        Theme.typography.h4.copy(
                            color = MivaColors.Secondary.BlueDeep,
                            fontWeight = FontWeight.Bold,
                        ),
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(end = Theme.spacing.x4),
                )
                Icon(
                    painterResource(if (isExpanded) R.drawable.ic_minus else R.drawable.ic_plus),
                    contentDescription = "Expand",
                    tint = Color.Unspecified,
                    modifier = Modifier.clickable { onClick() },
                )
            }
            AnimatedVisibility(visible = isExpanded) {
                Text(
                    text = answer,
                    style =
                        Theme.typography.body.copy(
                            color = MivaColors.Secondary.BlueDeep,
                        ),
                )
            }
        }
    }
}
