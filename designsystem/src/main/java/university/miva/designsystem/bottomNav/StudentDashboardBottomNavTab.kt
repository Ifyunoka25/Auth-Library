package university.miva.designsystem.bottomNav

import Theme
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import university.miva.designsystem.R
import university.miva.designsystem.components.text.BasicText
import university.miva.designsystem.theme.MivaColors

sealed class StudentDashboardBottomNavTab(
    val id: Int,
    @StringRes val title: Int,
    @DrawableRes val filledIcon: Int,
    @DrawableRes val borderIcon: Int,
) {
    data object Home : StudentDashboardBottomNavTab(
        id = 0,
        title = R.string.home,
        filledIcon = R.drawable.home_filled,
        borderIcon = R.drawable.home_border,
    )

    data object Courses : StudentDashboardBottomNavTab(
        id = 1,
        title = R.string.courses,
        filledIcon = R.drawable.ic_book_dark,
        borderIcon = R.drawable.ic_course_light,
    )

    data object Inbox : StudentDashboardBottomNavTab(
        id = 2,
        title = R.string.inbox,
        filledIcon = R.drawable.ic_inbox_dark,
        borderIcon = R.drawable.ic_inbox_light,
    )

    data object Profile : StudentDashboardBottomNavTab(
        id = 3,
        title = R.string.profile,
        filledIcon = R.drawable.ic_account_dark,
        borderIcon = R.drawable.ic_account_light,
    )

    companion object {
        fun fromID(id: Int): StudentDashboardBottomNavTab? =
            when (id) {
                Home.id -> Home
                Courses.id -> Courses
                Inbox.id -> Inbox
                Profile.id -> Profile
                else -> null
            }
    }
}

private fun StudentDashboardBottomNavTab.icon(selected: Boolean): Int =
    when {
        selected -> filledIcon
        else -> borderIcon
    }

@Composable
fun StudentBottomNavigation(
    selectedItem: StudentDashboardBottomNavTab,
    hasUnreadInbox: Boolean,
    onSelectItem: (StudentDashboardBottomNavTab) -> Unit,
) {
    Column {
        HorizontalDivider(
            color = MivaColors.Secondary.DividerColor,
        )
        NavigationBar(
            containerColor = MivaColors.White,
        ) {
            val tabItems =
                listOf(
                    StudentDashboardBottomNavTab.Home,
                    StudentDashboardBottomNavTab.Courses,
                    StudentDashboardBottomNavTab.Inbox,
                    StudentDashboardBottomNavTab.Profile,
                )
            for (entry in tabItems) {
                val selected = selectedItem == entry

                val iconRes =
                    when {
                        entry == StudentDashboardBottomNavTab.Inbox && hasUnreadInbox && selected ->
                            R.drawable.ic_unread_inbox_filled

                        entry == StudentDashboardBottomNavTab.Inbox && hasUnreadInbox && !selected ->
                            R.drawable.ic_unread_messages

                        else ->
                            entry.icon(selected)
                    }

                BottomNavigationBarItem(
                    iconRes = iconRes,
                    label = stringResource(entry.title),
                    selected = selected,
                    onClick = { onSelectItem(entry) },
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.BottomNavigationBarItem(
    @DrawableRes iconRes: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.2f else 1f,
    )

    Column(
        modifier =
            Modifier
                .weight(1f)
                .combinedClickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onClick,
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Theme.spacing.x1),
    ) {
        val contentColor =
            if (selected) MivaColors.Secondary.BlueDeep else MivaColors.Secondary.NeutralFive

        CompositionLocalProvider(
            LocalContentColor provides contentColor,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .size(Theme.spacing.x6)
                        .then(
                            if (selected &&
                                label == stringResource(R.string.profile)
                            ) {
                                Modifier.offset(x = 3.dp)
                            } else {
                                Modifier
                            },
                        ),
            ) {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.scale(scale),
                )
            }

            BasicText(
                text = label,
                style =
                    Theme.typography.bodyXs.copy(
                        color = contentColor,
                        textAlign = TextAlign.Center,
                    ),
            )
        }
    }
}
