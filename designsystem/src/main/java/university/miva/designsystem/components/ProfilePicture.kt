package university.miva.designsystem.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import university.miva.designsystem.R

@Composable
fun ProfilePicture(
    displayPicture: String,
    onProfileImageClick: () -> Unit = {},
) {
    val painter =
        rememberAsyncImagePainter(
            model = displayPicture.ifEmpty { R.drawable.ic_default_profile_picture },
            error = painterResource(R.drawable.ic_default_profile_picture),
            fallback = painterResource(R.drawable.ic_default_profile_picture),
        )
    Image(
        painter = painter,
        contentDescription = stringResource(R.string.profile_picture),
        contentScale = ContentScale.Crop,
        modifier =
            Modifier
                .size(Theme.spacing.x10)
                .clip(CircleShape)
                .clickable { onProfileImageClick() },
    )
}
