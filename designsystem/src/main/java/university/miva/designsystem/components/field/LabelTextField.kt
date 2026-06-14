package university.miva.designsystem.components.field

import Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import university.miva.designsystem.R
import university.miva.designsystem.components.field.internal.LocalTextFieldColorsProvider
import university.miva.designsystem.components.field.internal.ProvideTextFieldColors
import university.miva.designsystem.components.field.internal.componentColors
import university.miva.designsystem.components.image.Icon
import university.miva.designsystem.components.text.BasicText
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.theme.isSystemInDarkTheme

const val INPUT_MIN_HEIGHT = 56

@Composable
fun LabelTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    footerText: String? = null,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    textStyle: TextStyle = Theme.typography.bodyM,
    minLine: Int = 1,
    maxLine: Int = 1,
    focusState: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingContent: @Composable (RowScope.() -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    onPressedChanged: ((Boolean) -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    invertedColors: Boolean = isSystemInDarkTheme(),
    showErrorBorder: Boolean = false,
    unfocusedBorderColor: Color = MivaColors.White,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isFocused by remember { mutableStateOf(focusState) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    onPressedChanged?.invoke(true)
                }

                is FocusInteraction.Focus -> {
                    onFocusChanged?.invoke(true)
                    isFocused = true
                }

                is FocusInteraction.Unfocus -> {
                    onFocusChanged?.invoke(false)
                    isFocused = false
                }
            }
        }
    }

    val showPlaceholder = value.isEmpty()
    val borderColor =
        when {
            isFocused -> MivaColors.Primary.MainBlue
            footerText != null || showErrorBorder -> MivaColors.ErrorRed
            else -> unfocusedBorderColor
        }

    ProvideTextFieldColors(colors = componentColors(invertedColors)) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = modifier,
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = INPUT_MIN_HEIGHT.dp)
                        .background(MivaColors.White, shape = RoundedCornerShape(Theme.spacing.x2))
                        .border(1.dp, borderColor, shape = RoundedCornerShape(Theme.spacing.x2))
                        .padding(vertical = Theme.spacing.x4, horizontal = Theme.spacing.x3),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leadingContent?.let { leadingContent ->
                    leadingContent.invoke(this)
                    Spacer(modifier = Modifier.width(Theme.spacing.x2))
                }
                Box(
                    modifier =
                        modifier
                            .fillMaxWidth()
                            .weight(1f),
                ) {
                    if (showPlaceholder) {
                        BasicText(
                            text = placeholder,
                            style =
                                Theme.typography.bodyM.copy(
                                    color = LocalTextFieldColorsProvider.current.placeholderColor,
                                ),
                            modifier = Modifier.focusProperties { canFocus = false },
                        )
                    }
                    CompositionLocalProvider(
                        LocalTextSelectionColors provides
                            provideTextSelectionColor(
                                invertedColors,
                            ),
                    ) {
                        BasicTextField(
                            value = value,
                            onValueChange = onValueChange,
                            textStyle = textStyle,
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = singleLine,
                            minLines = minLine,
                            maxLines = maxLine,
                            readOnly = readOnly,
                            cursorBrush = SolidColor(LocalTextFieldColorsProvider.current.cursorColor),
                            interactionSource = interactionSource,
                            keyboardOptions = keyboardOptions,
                            keyboardActions = keyboardActions,
                            visualTransformation = visualTransformation,
                        )
                    }
                }
                trailingContent?.invoke()
            }
            footerText?.let { errorText ->
                Spacer(modifier = Modifier.height(Theme.spacing.x1))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(image = R.drawable.ic_error_close, modifier = Modifier.size(Theme.spacing.x3))
                    Spacer(modifier = Modifier.width(Theme.spacing.x1))
                    BasicText(
                        text = errorText,
                        style = Theme.typography.labelS.copy(color = MivaColors.ErrorRed),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(
    value: TextFieldValue,
    state: RichTextState,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    footerText: String? = null,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    textStyle: TextStyle = Theme.typography.bodyM,
    minLine: Int = 1,
    maxLine: Int = 1,
    focusState: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    leadingContent: @Composable (RowScope.() -> Unit)? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    onPressedChanged: ((Boolean) -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    invertedColors: Boolean = isSystemInDarkTheme(),
    showErrorBorder: Boolean = false,
    unfocusedBorderColor: Color = MivaColors.White,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isFocused by remember { mutableStateOf(focusState) }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    onPressedChanged?.invoke(true)
                }

                is FocusInteraction.Focus -> {
                    onFocusChanged?.invoke(true)
                    isFocused = true
                }

                is FocusInteraction.Unfocus -> {
                    onFocusChanged?.invoke(false)
                    isFocused = false
                }
            }
        }
    }
    val borderColor =
        when {
            isFocused -> MivaColors.Primary.MainBlue
            footerText != null || showErrorBorder -> MivaColors.ErrorRed
            else -> unfocusedBorderColor
        }

    ProvideTextFieldColors(colors = componentColors(invertedColors)) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = modifier,
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = INPUT_MIN_HEIGHT.dp)
                        .background(MivaColors.White, shape = RoundedCornerShape(Theme.spacing.x2))
                        .border(1.dp, borderColor, shape = RoundedCornerShape(Theme.spacing.x2))
                        .padding(vertical = Theme.spacing.x4, horizontal = Theme.spacing.x1),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                leadingContent?.let { leadingContent ->
                    leadingContent.invoke(this)
                    Spacer(modifier = Modifier.width(Theme.spacing.x2))
                }
                Box(
                    modifier =
                        modifier
                            .fillMaxWidth()
                            .weight(1f),
                ) {
                    CompositionLocalProvider(
                        LocalTextSelectionColors provides
                            provideTextSelectionColor(
                                invertedColors,
                            ),
                    ) {
                        RichTextEditor(
                            state = state,
                            textStyle = textStyle,
                            modifier = Modifier.fillMaxSize(),
                            singleLine = singleLine,
                            minLines = minLine,
                            maxLines = maxLine,
                            readOnly = readOnly,
                            placeholder = {
                                BasicText(
                                    text = placeholder,
                                    style =
                                        Theme.typography.bodyM.copy(
                                            color = MivaColors.Secondary.Gray500,
                                        ),
                                    modifier = Modifier.focusProperties { canFocus = false },
                                )
                            },
                            interactionSource = interactionSource,
                            keyboardOptions = keyboardOptions,
                            keyboardActions = keyboardActions,
                            colors =
                                RichTextEditorDefaults.richTextEditorColors(
                                    containerColor = MivaColors.White,
                                    focusedIndicatorColor = MivaColors.Secondary.Transparent,
                                    unfocusedIndicatorColor = MivaColors.Secondary.Transparent,
                                ),
                        )
                    }
                }
                trailingContent?.invoke()
            }
        }
    }
}

@Composable
internal fun provideTextSelectionColor(invertedColors: Boolean): TextSelectionColors =
    TextSelectionColors(
        handleColor = LocalTextFieldColorsProvider.current.cursorColor,
        backgroundColor = LocalTextFieldColorsProvider.current.selectionBackgroundColor,
    )

@Composable
private fun Boolean.toTextColor() =
    when (this) {
        true -> LocalTextFieldColorsProvider.current.errorColor
        false -> LocalTextFieldColorsProvider.current.footerTextColor
    }
