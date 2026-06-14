package university.miva.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.R as DesignSystemR

@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = message,
                style =
                    Theme.typography.bodyM.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = MivaColors.TextColors.Red500,
                    ),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x2))
            Button(onClick = onRetry) {
                Text(stringResource(DesignSystemR.string.retry))
            }
        }
    }
}
