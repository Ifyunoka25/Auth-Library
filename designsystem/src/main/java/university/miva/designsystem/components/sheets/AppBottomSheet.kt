package university.miva.designsystem.components.sheets

import Theme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    visible: Boolean,
    onFinalDismiss: () -> Unit,
    content: @Composable () -> Unit,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    header: (@Composable () -> Unit)? = null,
) {
    if (!visible) return

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = MivaColors.White,
        dragHandle = dragHandle,
        modifier = Modifier.statusBarsPadding(),
        onDismissRequest = {
            scope.launch {
                sheetState.hide()
                onFinalDismiss()
            }
        },
    ) {
        Column {
            if (header != null) {
                header()
            }
            if (header != null) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MivaColors.Secondary.NeutralSeven,
                )
            }

            content()
        }
    }
}

@Composable
fun AppBottomSheetHeader(
    onDismiss: () -> Unit,
    title: String,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.spacing.x5),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style =
                Theme.typography.headingM.copy(
                    fontWeight = FontWeight.Bold,
                    color = MivaColors.Black,
                    fontSize = 20.sp,
                ),
        )

        IconButton(onClick = onDismiss) {
            Icon(
                painterResource(R.drawable.ic_close_todo),
                contentDescription = stringResource(R.string.close_icon),
                tint = MivaColors.Secondary.BlueDeep,
                modifier =
                    Modifier
                        .clip(shape = CircleShape)
                        .clickable { onDismiss() }
                        .padding(Theme.spacing.x2),
            )
        }
    }
}
