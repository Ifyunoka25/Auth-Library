package university.miva.designsystem.components.dialog.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import university.miva.designsystem.theme.MivaColors

@Immutable
data class PopupModel(
    val headerImage: Int? = null,
    val title: String,
    val subtitle: String,
    val dismissible: Boolean = true,
)

@Immutable
data class DropdownMenuItemData(
    val title: String,
    val iconRes: Int? = null,
    val onClick: () -> Unit,
    val color: Color = MivaColors.Primary.MainBlue,
)
