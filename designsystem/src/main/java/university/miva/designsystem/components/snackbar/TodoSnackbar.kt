package university.miva.designsystem.components.snackbar

import Theme
import Theme.shape
import Theme.typography
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.components.image.Icon
import university.miva.designsystem.theme.MivaColors

@Composable
fun TodoSnackbar(
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(Theme.spacing.x4),
    ) {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .background(
                        color = MivaColors.Secondary.Red500,
                        shape = shape.roundShape,
                    ).border(
                        1.dp,
                        color = MivaColors.Secondary.Red500,
                        shape = shape.roundShape,
                    ).padding(vertical = Theme.spacing.x3, horizontal = Theme.spacing.x4),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                image = R.drawable.ic_info,
                modifier = Modifier.size(Theme.spacing.x6),
                colorFilter = ColorFilter.tint(MivaColors.White),
            )
            Spacer(modifier = Modifier.width(Theme.spacing.x3))
            Text(
                text = message,
                style =
                    typography.bodyM.copy(
                        color = MivaColors.White,
                        lineHeight = 24.sp,
                    ),
            )
        }
    }
}

@Composable
fun SnackbarHostWithTodoSnackbar(
    snackbarHostState: SnackbarHostState,
    isError: Boolean = true,
) {
    SnackbarHost(
        hostState = snackbarHostState,
    ) { data ->
        TodoSnackbar(message = data.visuals.message)
    }
}

@Composable
@Preview
fun PreviewTodoSnackbar() {
    TodoSnackbar("An error occurred. Please check your input.")
}
