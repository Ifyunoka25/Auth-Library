package university.miva.designsystem.components.image

import Theme
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import university.miva.designsystem.R
import university.miva.designsystem.components.text.BasicText
import university.miva.designsystem.theme.MivaColors

@Composable
fun Image(
    image: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    Image(
        painter = rememberAsyncImagePainter(image),
        modifier = modifier,
        contentScale = contentScale,
        contentDescription = contentDescription,
        alpha = alpha,
        colorFilter = colorFilter,
    )
}

@Composable
fun NetworkImage(
    image: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    @DrawableRes error: Int = R.drawable.course_details_bg,
) {
    val context = LocalContext.current
    val imageLoader =
        remember {
            ImageLoader
                .Builder(context)
                .components {
                    add(SvgDecoder.Factory())
                }.build()
        }

    Image(
        painter =
            rememberAsyncImagePainter(
                ImageRequest
                    .Builder(context)
                    .data(image)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .placeholder(error)
                    .error(error)
                    .fallback(error)
                    .build(),
                imageLoader = imageLoader,
            ),
        modifier = modifier,
        contentScale = contentScale,
        contentDescription = contentDescription,
        alpha = alpha,
        colorFilter = colorFilter,
    )
}

@Composable
fun ClickableSpanImage(
    image: Int,
    contentDescription: String? = null,
    tint: Color? = null,
    rippleColor: Color = MivaColors.Secondary.Blue200,
    modifier: Modifier = Modifier,
    clickable: Boolean = true,
    onClick: () -> Unit,
) {
    Image(
        painterResource(id = image),
        contentDescription = contentDescription,
        colorFilter = tint?.let { ColorFilter.tint(it) },
        modifier =
            modifier
                .clip(shape = CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication =
                        ripple(
                            color = rippleColor,
                        ),
                ) { onClick() }
                .padding(Theme.spacing.x3),
    )
}

@Composable
fun NotClickableSpanImage(
    image: Int,
    contentDescription: String? = null,
    tint: Color? = null,
    modifier: Modifier = Modifier,
) {
    Image(
        painterResource(id = image),
        contentDescription = contentDescription,
        colorFilter = tint?.let { ColorFilter.tint(it) },
        modifier = modifier.padding(Theme.spacing.x3),
    )
}

@Composable
fun Icon(
    image: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    adaptive: Boolean = false,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    onClick: OptionalButtonCallback = null,
    invertedColors: Boolean = isSystemInDarkTheme(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val iconColorFilter =
        colorFilter ?: ColorFilter
            .tint(
                color =
                    when (invertedColors) {
                        true -> Theme.colors.element.primaryOnDark
                        false -> Theme.colors.element.primaryOnLight
                    },
            ).takeIf { adaptive }

    Image(
        image = image,
        modifier =
            if (onClick == null) {
                modifier
            } else {
                modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = true,
                    onClick = {
                        onClick.invoke()
                    },
                )
            },
        contentDescription = contentDescription,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = iconColorFilter,
    )
}

@Composable
fun ImageUrl(
    imageUrl: Any,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
) {
    AsyncImage(
        model =
            ImageRequest
                .Builder(LocalContext.current)
                .data(imageUrl)
                .setHeader("User-Agent", "Mozilla/5.0")
                .crossfade(true)
                .build(),
        modifier = modifier,
        contentDescription = contentDescription,
        alignment = alignment,
        contentScale = contentScale,
    )
}

@Composable
fun SquareImageBackground(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        modifier = modifier.clip(RectangleShape),
        contentDescription = contentDescription,
        alignment = alignment,
        contentScale = contentScale,
    )
}

@Composable
fun CircleInitialsAvatar(
    initials: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(Theme.spacing.x9)
                .clip(CircleShape)
                .background(MivaColors.Secondary.QuickActionsBgColor),
        contentAlignment = Alignment.Center,
    ) {
        BasicText(
            initials,
            style =
                Theme.typography.h1.copy(
                    fontSize = 16.sp,
                ),
        )
    }
}

@Composable
fun DoubleBorderInitialsAvatar(
    size: Dp = 44.dp,
    initials: String,
    outerBorderColor: Color = MivaColors.Secondary.Green500,
    textStyle: TextStyle = Theme.typography.h1.copy(fontSize = 14.sp, lineHeight = 20.sp),
    textPadding: Dp = Theme.spacing.x2,
) {
    Box(
        modifier =
            Modifier
                .size(size)
                .clip(CircleShape)
                .border(1.dp, outerBorderColor, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(MivaColors.Secondary.QuickActionsBgColor)
                    .border(1.dp, MivaColors.White, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            BasicText(
                initials,
                style = textStyle,
                modifier = Modifier.padding(textPadding),
            )
        }
    }
}

typealias OptionalButtonCallback = (() -> Unit)?

fun OptionalButtonCallback.isEnabled(): Boolean = this != null
