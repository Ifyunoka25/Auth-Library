package university.miva.designsystem.components.dialog

import Theme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import university.miva.designsystem.R
import university.miva.designsystem.components.dialog.model.RepeatInterval
import university.miva.designsystem.theme.MivaColors

@Composable
fun RepeatIntervalDialog(
    currentFilter: RepeatInterval,
    onFilterSelected: (RepeatInterval) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            modifier =
                Modifier
                    .padding(vertical = Theme.spacing.x6),
            shape = RoundedCornerShape(Theme.spacing.x2),
            colors =
                CardDefaults.cardColors().copy(
                    containerColor = MivaColors.White,
                ),
        ) {
            Column(
                modifier =
                    Modifier.padding(
                        vertical = Theme.spacing.x3,
                    ),
            ) {
                val filters =
                    listOf(
                        RepeatInterval.EVERY_DAY,
                        RepeatInterval.EVERY_WEEK,
                        RepeatInterval.EVERY_TWO_WEEKS,
                        RepeatInterval.EVERY_MONTH,
                        RepeatInterval.EVERY_YEAR,
                    )

                filters.forEachIndexed { index, label ->
                    RepeatInterval(
                        label = label.title,
                        selected = label == currentFilter,
                        onClick = { onFilterSelected(label) },
                    )
                }
            }
        }
    }
}

@Composable
fun RepeatInterval(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = Theme.spacing.x3, horizontal = Theme.spacing.x6),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style =
                Theme.typography.bodyM.copy(
                    color = MivaColors.Secondary.Blue400,
                ),
        )

        Spacer(modifier = Modifier.weight(1f))
        if (selected) {
            Icon(
                painterResource(R.drawable.ic_selected),
                contentDescription = stringResource(R.string.overdue_icon),
                tint = Color.Unspecified,
            )
        }
    }
}
