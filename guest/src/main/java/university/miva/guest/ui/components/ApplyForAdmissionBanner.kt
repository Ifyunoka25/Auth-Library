package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import university.miva.designsystem.theme.MivaColors
import university.miva.guest.R
import university.miva.designsystem.R as DesignSystemR

@Composable
fun ApplyForAdmissionBanner(onClick: () -> Unit) {
    BoxWithConstraints(
        modifier =
            Modifier
                .clickable { onClick() },
    ) {
        val bWCScope = this
        val maxWidth = bWCScope.maxWidth
        val imageHeight =
            when {
                maxWidth < 350.dp -> 210.dp
                maxWidth < 400.dp -> 256.dp
                maxWidth < 500.dp -> 236.dp
                maxWidth < 600.dp -> 320.dp
                maxWidth < 700.dp -> 320.dp
                else -> 650.dp
            }

        val imageScale =
            when {
                maxWidth < 350.dp -> ContentScale.Fit
                maxWidth < 400.dp -> ContentScale.Crop
                maxWidth < 700.dp -> ContentScale.Crop
                else -> ContentScale.Crop
            }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        onClick()
                    },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_graduate_students),
                contentDescription = stringResource(DesignSystemR.string.graduates_students),
                contentScale = imageScale,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(imageHeight),
            )
            Box(
                modifier =
                    Modifier
                        .offset(y = (-64).dp)
                        .fillMaxWidth()
                        .padding(horizontal = Theme.spacing.x4)
                        .background(
                            color = MivaColors.TextColors.Red500,
                            shape = RoundedCornerShape(Theme.spacing.x2),
                        ).padding(25.dp),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(DesignSystemR.string.apply_for_admission_today),
                        style =
                            Theme.typography.h3.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            ),
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.x2))

                    Text(
                        text = stringResource(DesignSystemR.string.class_start),
                        style =
                            Theme.typography.bodyM.copy(
                                color = Color.White,
                            ),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
