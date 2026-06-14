import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import university.miva.designsystem.R
import university.miva.designsystem.components.image.NotClickableSpanImage

@Composable
fun CustomDropdownMenu(
    expanded: Boolean,
    isGroup: Boolean,
    onProfileClick: () -> Unit,
    onMuteClick: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = if (isGroup) stringResource(R.string.group_info) else stringResource(R.string.user_info)
    val muteChat = remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() },
        modifier = modifier,
    ) {
        Column {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            onProfileClick()
                        }.padding(vertical = Theme.spacing.x3),
            ) {
                Text(
                    text,
                    style =
                        Theme.typography.bodyS.copy(
                            fontWeight = FontWeight.Medium,
                        ),
                    modifier = Modifier.padding(start = Theme.spacing.x4),
                )
            }
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            onMuteClick(muteChat.value)
                            muteChat.value = !muteChat.value
                        },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    stringResource(if (muteChat.value) R.string.unmute else R.string.mute),
                    style =
                        Theme.typography.bodyS.copy(
                            fontWeight = FontWeight.Medium,
                        ),
                    modifier = Modifier.padding(start = Theme.spacing.x4),
                )
                Spacer(modifier = Modifier.width(Theme.spacing.x2))
                NotClickableSpanImage(if (muteChat.value) R.drawable.ic_mute else R.drawable.ic_unmute)
            }
        }
    }
}
