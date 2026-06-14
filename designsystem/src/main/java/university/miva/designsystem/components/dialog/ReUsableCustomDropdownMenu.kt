package university.miva.designsystem.components.dialog

import Theme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import university.miva.designsystem.components.dialog.model.DropdownMenuItemData
import university.miva.designsystem.components.image.NotClickableSpanImage

@Composable
fun ReUsableCustomDropdownMenu(
    expanded: Boolean,
    items: List<DropdownMenuItemData>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Column {
            items.forEach { item ->
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                item.onClick()
                            }.padding(horizontal = Theme.spacing.x1),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    item.iconRes?.let { res ->
                        NotClickableSpanImage(res)
                    }

                    Text(
                        text = item.title,
                        style = Theme.typography.bodyM.copy(color = item.color),
                    )
                }
            }
        }
    }
}
