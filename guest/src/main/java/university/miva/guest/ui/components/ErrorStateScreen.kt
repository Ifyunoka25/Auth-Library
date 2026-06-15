package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors

@Composable
fun ErrorStateScreen(
    modifier: Modifier = Modifier,
    message: String? = null,
    onRefreshClick: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = Theme.spacing.x6)
                .padding(top = Theme.spacing.x12),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier =
                Modifier
                    .clip(RoundedCornerShape(Theme.spacing.x4))
                    .background(MivaColors.White)
                    .padding(vertical = Theme.spacing.x10, horizontal = Theme.spacing.x6),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_guest_warning),
                contentDescription = stringResource(R.string.error_icon),
                modifier = Modifier.size(Theme.spacing.x16),
            )

            Spacer(modifier = Modifier.height(Theme.spacing.x3))

            Text(
                text = stringResource(R.string.oops_we_could_not_get_this_information),
                style = Theme.typography.headingM,
                color = MivaColors.TextColors.BlueDeep,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(Theme.spacing.x3))

            Text(
                text = message ?: stringResource(R.string.kindly_refresh_the_page_to_try_again_if_the_issue),
                style =
                    Theme.typography.bodyM.copy(
                        color = MivaColors.Secondary.ContentTextSecondary,
                    ),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(Theme.spacing.x6))

            Text(
                text = stringResource(R.string.refresh_page),
                modifier = Modifier.clickable { onRefreshClick() },
                style =
                    Theme.typography.headingM.copy(
                        color = MivaColors.Secondary.ContentTextLnk,
                        textDecoration = TextDecoration.Underline,
                    ),
                textAlign = TextAlign.Center,
            )
        }
    }
}
