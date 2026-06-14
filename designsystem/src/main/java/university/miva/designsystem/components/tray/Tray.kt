package university.miva.designsystem.components.tray

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import university.miva.designsystem.components.tray.base.BaseModelBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tray(
    onDismissRequest: OptionalButtonCallback,
    isSwipeDismissible: Boolean = false,
    skipPartiallyExpanded: Boolean = false,
    stickyBottom: (@Composable BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val sheetState =
        rememberModalBottomSheetState(
            confirmValueChange = { newState ->
                if (!isSwipeDismissible && newState == SheetValue.Hidden) {
                    false
                } else {
                    true
                }
            },
            skipPartiallyExpanded = skipPartiallyExpanded,
        )

    BaseModelBottomSheet(
        onDismissRequest = {
            onDismissRequest?.invoke()
        },
        sheetState = sheetState,
    ) {
        Box(
            modifier = Modifier.navigationBarsPadding(),
            contentAlignment = Alignment.BottomEnd,
        ) {
            content()
            stickyBottom?.let { stickyBottom ->
                Box(
                    modifier =
                        Modifier
                            .wrapContentWidth()
                            .padding(Theme.spacing.x5)
                            .offset {
                                IntOffset(x = 0, y = -sheetState.requireOffset().toInt())
                            },
                ) {
                    stickyBottom()
                }
            }
        }
    }
}

typealias OptionalButtonCallback = (() -> Unit)?

fun OptionalButtonCallback.isEnabled(): Boolean = this != null
