package university.miva.designsystem.components.dialog

import Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import university.miva.designsystem.R
import university.miva.designsystem.components.button.SmallDefaultButton
import university.miva.designsystem.components.button.model.ButtonModel
import university.miva.designsystem.components.dialog.model.PopupModel
import university.miva.designsystem.components.image.Image
import university.miva.designsystem.components.text.BasicText
import university.miva.designsystem.theme.MivaColors

@Composable
fun Popup(
    model: PopupModel,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Theme.spacing.x4))
                    .background(MivaColors.White)
                    .padding(Theme.spacing.x6),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            model.headerImage?.let {
                Image(
                    it,
                    contentDescription = stringResource(R.string.popup_image),
                    modifier = Modifier.size(120.dp),
                )
            }
            Spacer(modifier = Modifier.height(Theme.spacing.x6))
            BasicText(
                text = model.title,
                style =
                    Theme.typography.h1.copy(
                        fontSize = 20.sp,
                        color = MivaColors.TextColors.Blue500,
                    ),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x2))
            BasicText(
                text = model.subtitle,
                style =
                    Theme.typography.body.copy(
                        color = MivaColors.TextColors.Blue300,
                        textAlign = TextAlign.Center,
                    ),
            )
            Spacer(modifier = Modifier.height(Theme.spacing.x5))
            SmallDefaultButton(
                model = ButtonModel(title = stringResource(R.string.sign_in)),
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick,
            )
        }
    }
}
