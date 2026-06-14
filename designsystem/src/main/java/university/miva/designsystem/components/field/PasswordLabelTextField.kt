package university.miva.designsystem.components.field

import Theme
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import university.miva.designsystem.R
import university.miva.designsystem.components.image.Icon
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.passwordRegex

@Composable
fun PasswordLabelTextField(
    value: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    placeholder: String = "",
    footerText: String? = null,
    onValueChange: (String) -> Unit,
    useErrorMessage: Boolean = true,
    onPasswordToggleChange: ((Boolean) -> Unit)? = null,
) {
    val context = LocalContext.current
    var isPasswordVisible by remember { mutableStateOf(true) }
    var hasFocus by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun isValidPassword(password: String): Boolean = password.matches(passwordRegex.toRegex())

    LabelTextField(
        value = value,
        trailingContent = {
            PasswordIcon(
                isPasswordVisible = isPasswordVisible,
                onPasswordToggleChange = {
                    isPasswordVisible = !isPasswordVisible
                    onPasswordToggleChange?.invoke(isPasswordVisible)
                },
            )
        },
        placeholder = placeholder,
        visualTransformation =
            if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation('*')
            },
        singleLine = true,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        onValueChange = { newValue ->
            onValueChange.invoke(newValue)
            errorMessage =
                if (isValidPassword(newValue)) {
                    null
                } else {
                    if (useErrorMessage) {
                        context.getString(
                            R.string.password_must_contain_1_letter_1_number_1_special_char,
                        )
                    } else {
                        footerText
                    }
                }
        },
        footerText = errorMessage ?: footerText,
        modifier = modifier.onFocusChanged { hasFocus = it.hasFocus },
    )
}

@Composable
fun PasswordIcon(
    isPasswordVisible: Boolean,
    tint: Color = MivaColors.Secondary.BlueDeep,
    size: Dp = Theme.spacing.x6,
    onPasswordToggleChange: (Boolean) -> Unit,
) {
    AnimatedContent(
        targetState = isPasswordVisible,
        transitionSpec = {
            fadeIn(
                animationSpec = tween(durationMillis = 500),
            ).togetherWith(fadeOut(animationSpec = tween(durationMillis = 500)))
        },
        label = stringResource(R.string.password_visibility_image),
    ) { targetState ->
        Icon(
            image = if (targetState) R.drawable.ic_eye_on else R.drawable.ic_eye_off,
            modifier = Modifier.size(size),
            colorFilter = ColorFilter.tint(tint),
            onClick = {
                onPasswordToggleChange.invoke(isPasswordVisible)
            },
        )
    }
}
