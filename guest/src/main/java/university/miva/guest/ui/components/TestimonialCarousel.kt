package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import university.miva.designsystem.theme.MivaColors
import university.miva.guest.ui.viewmodel.Testimonial
import university.miva.designsystem.R as DesignSystemR

@Composable
fun TestimonialCarousel(
    testimonials: List<Testimonial>,
    shouldAnimate: Boolean,
    onPlay: (String, String) -> Unit,
    autoScrollInterval: Long = 4000L,
) {
    val pagerState = rememberLazyListState()
    val currentItem by remember {
        derivedStateOf {
            val layoutInfo = pagerState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) {
                0
            } else {
                val center = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.width / 2
                val item =
                    visibleItems.find { item ->
                        item.offset <= center && item.offset + item.size >= center
                    }
                item?.index ?: 0
            }
        }
    }

    // Auto-scroll effect
    LaunchedEffect(shouldAnimate, pagerState, testimonials.size) {
        if (shouldAnimate) {
            while (true) {
                delay(autoScrollInterval)
                if (testimonials.isNotEmpty()) {
                    val nextItem = (currentItem + 1) % testimonials.size
                    pagerState.animateScrollToItem(nextItem)
                }
            }
        }
    }

    Column(modifier = Modifier.padding(vertical = Theme.spacing.x3)) {
        Text(
            text = stringResource(DesignSystemR.string.miva_stories),
            style =
                Theme.typography.h4.copy(
                    color = MivaColors.TextColors.Blue500,
                    fontWeight = FontWeight.Bold,
                ),
            modifier = Modifier.padding(horizontal = Theme.spacing.x3),
        )
        Spacer(modifier = Modifier.height(26.dp))
        LazyRow(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = Theme.spacing.x3),
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.x3),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = pagerState),
        ) {
            items(testimonials.size) { index ->
                TestimonialCard(
                    testimonial = testimonials[index],
                    onPlay = onPlay,
                )
            }
        }

        Spacer(modifier = Modifier.height(Theme.spacing.x9))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(testimonials.size) { index ->

                val isSelected = currentItem == index
                Box(
                    modifier =
                        Modifier
                            .padding(Theme.spacing.x1)
                            .width(if (isSelected) 26.dp else Theme.spacing.x3)
                            .height(6.dp)
                            .background(
                                color =
                                    if (isSelected) {
                                        MivaColors.TextColors.BlueDeep
                                    } else {
                                        MivaColors.Secondary.UnselectedCarouselColor
                                    },
                                shape = CircleShape,
                            ),
                )
            }
        }
    }
}

@Composable
fun TestimonialCard(
    testimonial: Testimonial,
    onPlay: (String, String) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .widthIn(min = 300.dp, max = 300.dp)
                .clip(RoundedCornerShape(Theme.spacing.x2))
                .background(MivaColors.Secondary.UnselectedCarouselColor)
                .clickable { onPlay(testimonial.videoUrl, testimonial.thumbnail) },
    ) {
        val painter =
            rememberAsyncImagePainter(
                model = testimonial.thumbnail,
                onLoading = {},
            )
        Image(
            painter = painter,
            contentDescription = stringResource(DesignSystemR.string.youTube_video_thumbnail),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(202.dp),
            contentScale = ContentScale.Crop,
        )
        Image(
            painterResource(DesignSystemR.drawable.ic_play),
            contentDescription = stringResource(DesignSystemR.string.play_icon),
            modifier =
                Modifier
                    .align(Alignment.Center)
                    .size(Theme.spacing.x12),
        )
    }
}
