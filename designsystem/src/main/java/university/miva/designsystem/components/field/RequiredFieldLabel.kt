package university.miva.designsystem.components.field

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import university.miva.designsystem.R
import university.miva.designsystem.theme.MivaColors

@Composable
fun RequiredFieldLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text =
            buildAnnotatedString {
                append(text)
                withStyle(SpanStyle(color = MivaColors.ErrorRed)) {
                    append("*")
                }
            },
        style =
            Theme.typography.bodyS.copy(
                fontWeight = FontWeight.Medium,
                color = MivaColors.Secondary.BlueDeep,
            ),
        modifier = modifier,
    )
}

@Composable
fun OptionalFieldLabel(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "$text ${stringResource(R.string.optional_)}",
        style =
            Theme.typography.bodyS.copy(
                fontWeight = FontWeight.Medium,
                color = MivaColors.Secondary.BlueDeep,
            ),
        modifier = modifier,
    )
}
