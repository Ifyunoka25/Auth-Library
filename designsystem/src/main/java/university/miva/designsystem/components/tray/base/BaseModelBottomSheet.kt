package university.miva.designsystem.components.tray.base

import Theme
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import university.miva.designsystem.components.tray.OptionalButtonCallback
import university.miva.designsystem.theme.isSystemInDarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BaseModelBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismissRequest: OptionalButtonCallback,
    invertedColors: Boolean = isSystemInDarkTheme(),
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        containerColor = componentColors(invertedColors = invertedColors),
        onDismissRequest = {
            onDismissRequest?.invoke()
        },
        sheetState = sheetState,
        modifier = modifier,
        dragHandle = null,
    ) {
        content()
    }
}

@Composable
private fun componentColors(invertedColors: Boolean) =
    when (invertedColors) {
        true -> Theme.colors.surface.primaryDark
        false -> Theme.colors.surface.primaryLight
    }
