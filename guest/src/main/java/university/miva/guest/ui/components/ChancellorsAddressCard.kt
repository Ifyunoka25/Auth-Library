package university.miva.guest.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import university.miva.designsystem.R as DesignSystemR

@Composable
fun ChancellorsAddressCard(modifier: Modifier = Modifier) {
    BoxWithConstraints {
        val maxWidth = this.maxWidth

        val imageHeight =
            when {
                maxWidth < 200.dp -> 75.dp
                maxWidth < 400.dp -> 95.dp
                maxWidth < 610.dp -> 160.dp
                else -> 200.dp
            }

        Image(
            painter = painterResource(id = DesignSystemR.drawable.ic_chancellor_bcg),
            contentDescription = stringResource(DesignSystemR.string.chancellor_address_background),
            contentScale = ContentScale.Crop,
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(imageHeight),
        )
    }
}

@Preview(
    name = "Landscape",
    group = "BoxWithConstraints",
    widthDp = 1248,
    heightDp = 360,
)
@Preview(
    name = "Portrait600",
    group = "BoxWithConstraints",
    widthDp = 601,
    heightDp = 640,
)
@Preview(
    name = "Portrait350",
    group = "BoxWithConstraints",
    widthDp = 350,
    heightDp = 640,
)
@Composable
fun AdaptiveChancellorsAddressCardPreview() {
    ChancellorsAddressCard()
}
