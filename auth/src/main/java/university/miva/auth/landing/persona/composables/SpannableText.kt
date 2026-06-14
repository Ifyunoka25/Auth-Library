package university.miva.auth.landing.persona.composables

import Theme
import android.content.Intent
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.core.net.toUri
import university.miva.auth.R
import university.miva.designsystem.theme.MivaColors

@Composable
fun SpannableText() {
    val context = LocalContext.current
    val spannableText =
        buildAnnotatedString {
            append(stringResource(R.string.miva_is_licensed_by_the))

            pushStringAnnotation(tag = "NUC", annotation = "https://www.nuc.edu.ng")
            withStyle(
                style =
                    SpanStyle(
                        color = MivaColors.White,
                        fontWeight = FontWeight.Bold,
                    ),
            ) {
                append(stringResource(R.string.national_universities_commission))
            }
            pop()
        }

    ClickableText(
        text = spannableText,
        style = Theme.typography.bodyM.copy(color = MivaColors.White),
        onClick = { offset ->
            spannableText
                .getStringAnnotations(tag = "NUC", start = offset, end = offset)
                .firstOrNull()
                ?.let {
                    val intent = Intent(Intent.ACTION_VIEW, it.item.toUri())
                    context.startActivity(intent)
                }
        },
    )
}
