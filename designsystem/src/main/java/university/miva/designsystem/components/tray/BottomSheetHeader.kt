package university.miva.designsystem.components.tray

import Theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import university.miva.designsystem.R
import university.miva.designsystem.components.divider.Divider
import university.miva.designsystem.components.image.ClickableSpanImage
import university.miva.designsystem.components.text.BasicText
import university.miva.designsystem.theme.MivaColors

@Composable
fun BottomSheetHeader(
    title: String,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(top = Theme.spacing.x4, start = Theme.spacing.x4, end = Theme.spacing.x4),
        ) {
            BasicText(
                text = title,
                style =
                    Theme.typography.h4.copy(
                        color = MivaColors.Black,
                        fontWeight = FontWeight.Bold,
                    ),
                modifier = Modifier.padding(top = Theme.spacing.x3),
            )
            ClickableSpanImage(
                image = R.drawable.ic_close,
                contentDescription = stringResource(R.string.close),
            ) { onDismiss.invoke() }
        }
        Spacer(modifier = Modifier.height(Theme.spacing.x1))
        Divider(
            modifier =
                Modifier
                    .height(2.dp),
            color = MivaColors.Secondary.BorderColor,
        )
    }
}
