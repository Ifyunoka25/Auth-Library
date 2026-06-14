package university.miva.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.components.text.BasicText
import university.miva.designsystem.theme.MivaColors

@Composable
fun CircleNameInitialsAvatar(initials: String) {
    Box(
        modifier =
            Modifier
                .size(36.dp)
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
