package university.miva.designsystem.components.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest

@Composable
fun GifImage(
    url: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val imageLoader =
        remember {
            ImageLoader
                .Builder(context)
                .components {
                    add(GifDecoder.Factory())
                }.build()
        }

    AsyncImage(
        model =
            ImageRequest
                .Builder(context)
                .data(url)
                .crossfade(true)
                .build(),
        contentDescription = "Animated GIF",
        modifier = modifier,
        imageLoader = imageLoader,
    )
}
