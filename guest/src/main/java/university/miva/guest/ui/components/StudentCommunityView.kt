package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.theme.MivaColors
import university.miva.guest.R
import university.miva.designsystem.R as DesignSystemR

@Composable
fun StudentCommunityView() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MivaColors.Secondary.Gray200),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(51.dp))
        Text(
            text = stringResource(DesignSystemR.string.join_our_student_community),
            modifier = Modifier.padding(horizontal = 23.dp),
            style =
                Theme.typography.h3.copy(
                    fontWeight = FontWeight.Bold,
                    color = MivaColors.TextColors.BlueDeep,
                ),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(Theme.spacing.x4))
        Text(
            text = stringResource(DesignSystemR.string.connect_with_like_minded_individuals),
            modifier = Modifier.padding(horizontal = Theme.spacing.x8),
            style =
                Theme.typography.h3.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MivaColors.TextColors.Blue400,
                ),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(43.dp))
        Image(
            painterResource(id = R.drawable.ic_student_community),
            contentDescription = stringResource(DesignSystemR.string.student_community),
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(47.dp))
    }
}
