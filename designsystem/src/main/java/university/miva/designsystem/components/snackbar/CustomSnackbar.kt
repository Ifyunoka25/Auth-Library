package university.miva.designsystem.components.snackbar

import Theme
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.components.image.Icon
import university.miva.designsystem.theme.MivaColors

@Composable
fun CustomSnackbar(
    message: String,
    modifier: Modifier = Modifier,
    isError: Boolean = true,
    isDraft: Boolean = false,
    isInternetError: Boolean = false,
    contentPadding: Dp = Theme.spacing.x4,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(contentPadding),
    ) {
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .background(
                        color =
                            if (isError) {
                                MivaColors.Secondary.Red50
                            } else if (isDraft) {
                                MivaColors.Secondary.SentLight
                            } else if (isInternetError) {
                                MivaColors.Secondary.Gray100
                            } else {
                                MivaColors.Secondary.Green50
                            },
                        shape = RoundedCornerShape(Theme.spacing.x3),
                    ).border(
                        1.dp,
                        color =
                            if (isError) {
                                MivaColors.TextColors.Red500
                            } else if (isDraft) {
                                MivaColors.Secondary.BlueDeep
                            } else if (isInternetError) {
                                MivaColors.Secondary.InternetBorderColor
                            } else {
                                MivaColors.Secondary.Green500
                            },
                        shape = RoundedCornerShape(Theme.spacing.x3),
                    ).padding(vertical = Theme.spacing.x3, horizontal = Theme.spacing.x4),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                image =
                    if (isError) {
                        R.drawable.ic_error_alerts
                    } else if (isDraft) {
                        R.drawable.ic_gray_alert
                    } else if (isInternetError) {
                        R.drawable.ic_internet_alert
                    } else {
                        R.drawable.ic_alert_green
                    },
                modifier = Modifier.size(Theme.spacing.x6),
            )
            Spacer(modifier = Modifier.width(Theme.spacing.x3))
            Text(
                text = message,
                style =
                    typography.bodyS.copy(
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Medium,
                    ),
            )
        }
    }
}

@Composable
fun SnackbarHostWithCustomSnackbar(
    snackbarHostState: SnackbarHostState,
    isError: Boolean = true,
    isInternetError: Boolean = false,
) {
    SnackbarHost(
        hostState = snackbarHostState,
    ) { data ->
        CustomSnackbar(message = data.visuals.message, isInternetError = isInternetError, isError = isError)
    }
}

@Composable
fun InternetSnackbarHost(
    snackbarHostState: SnackbarHostState,
    isError: Boolean = false,
) {
    SnackbarHost(
        hostState = snackbarHostState,
    ) { data ->
        CustomSnackbar(message = data.visuals.message, isInternetError = true, isError = isError)
    }
}

@Composable
@Preview
fun PreviewCustomSnackbar() {
    CustomSnackbar("An error occurred. Please check your input.")
}
