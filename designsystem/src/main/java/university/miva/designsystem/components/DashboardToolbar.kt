package university.miva.designsystem.components

import Theme
import android.view.View
import android.widget.FrameLayout
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors

@Composable
fun DashboardToolbar(
    name: String,
    profileImage: String,
    showNotification: Boolean = false,
    unreadCount: Int = 0,
    showHeaderContent: Boolean = true,
    onNotificationClick: () -> Unit = {},
    onProfileImageClick: () -> Unit = {},
    paddingBottom: Dp? = null,
    toolBarPaddingTop: Dp? = null,
    toolBarHeight: Dp? = null,
    statusBarPadding: Dp = 50.dp,
    onNotificationAnchorReady: (View) -> Unit = {},
    onDashboardAnchorReady: (View) -> Unit = {},
    content: @Composable (() -> Unit)? = null,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(
                    MivaColors.Secondary.BlueDeep,
                    shape =
                        RoundedCornerShape(
                            bottomStart = Theme.spacing.x8,
                            bottomEnd = Theme.spacing.x8,
                        ),
                ).padding(top = toolBarPaddingTop ?: Theme.spacing.x14)
                .padding(top = statusBarPadding),
        contentAlignment = Alignment.BottomStart,
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_toolbar),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize(),
        )

        Column(
            modifier =
                Modifier
                    .animateContentSize(),
        ) {
            AnimatedVisibility(
                visible = showHeaderContent,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -it / 6 }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { -it / 6 }),
            ) {
                Row(
                    modifier =
                        Modifier.padding(
                            start = Theme.spacing.x4,
                            bottom = paddingBottom ?: Theme.spacing.x4,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ProfilePicture(profileImage, onProfileImageClick)
                    Spacer(modifier = Modifier.width(Theme.spacing.x2))

                    Box {
                        AndroidView(
                            modifier = Modifier.matchParentSize(),
                            factory = { ctx ->
                                FrameLayout(ctx).apply {
                                    isClickable = false
                                    isFocusable = false
                                    importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
                                    post { onDashboardAnchorReady(this) }
                                }
                            },
                        )

                        Text(
                            text = stringResource(R.string.welcome, name),
                            style =
                                Theme.typography.body.copy(
                                    color = MivaColors.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (showNotification) {
                        Box(
                            modifier =
                                Modifier
                                    .wrapContentSize()
                                    .clip(CircleShape)
                                    .clickable { onNotificationClick() },
                        ) {
                            AndroidView(
                                modifier = Modifier.matchParentSize(),
                                factory = { ctx ->
                                    FrameLayout(ctx).apply {
                                        isClickable = false
                                        isFocusable = false
                                        importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
                                        post { onNotificationAnchorReady(this) }
                                    }
                                },
                            )
                            Icon(
                                painterResource(
                                    if (unreadCount > 0) {
                                        R.drawable.ic_unread_notification
                                    } else {
                                        R.drawable.ic_notification
                                    },
                                ),
                                contentDescription = stringResource(R.string.notifications),
                                tint = Color.Unspecified,
                                modifier = Modifier.padding(Theme.spacing.x4),
                            )
                        }
                    }
                }
            }

            content?.invoke()
        }
    }
}
