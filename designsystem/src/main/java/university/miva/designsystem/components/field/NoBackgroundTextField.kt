package university.miva.designsystem.components.field

import Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.components.text.BasicText
import university.miva.designsystem.theme.MivaColors

@Composable
fun EditableTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = stringResource(R.string.tap_to_add_description),
    label: String = "",
    textSize: TextUnit = 14.sp,
    fontWeight: FontWeight = FontWeight.SemiBold,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
) {
    Column {
        if (label.isNotEmpty()) {
            BasicText(
                text = label,
                style =
                    Theme.typography.labelS.copy(
                        color = MivaColors.TextColors.Blue200,
                    ),
            )
            Spacer(modifier = modifier.height(Theme.spacing.x3))
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(MivaColors.Secondary.Transparent),
            textStyle =
                Theme.typography.bodyS.copy(
                    fontWeight = fontWeight,
                    fontSize = textSize,
                ),
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            cursorBrush = SolidColor(MivaColors.Primary.MainBlue),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    BasicText(
                        text = hint,
                        style =
                            Theme.typography.bodyS.copy(
                                fontWeight = fontWeight,
                                color = MivaColors.TextColors.Gray700,
                                fontSize = textSize,
                            ),
                    )
                }
                innerTextField()
            },
        )
    }
}
