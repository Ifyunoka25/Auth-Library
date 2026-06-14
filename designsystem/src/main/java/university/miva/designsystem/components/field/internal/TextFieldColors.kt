package university.miva.designsystem.components.field.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import university.miva.designsystem.theme.MivaColors

internal data class TextFieldColors(
    val labelColor: Color = Color.Unspecified,
    val placeholderColor: Color = Color.Unspecified,
    val cursorColor: Color = Color.Unspecified,
    val errorColor: Color = Color.Unspecified,
    val footerTextColor: Color = Color.Unspecified,
    val focusedBorderColor: Color = Color.Unspecified,
    val unfocusedBorderColor: Color = Color.Unspecified,
    val focusedIndicatorColor: Color = Color.Unspecified,
    val unfocusedIndicatorColor: Color = Color.Unspecified,
    val selectionBackgroundColor: Color = Color.Unspecified,
    val leadingContentColor: Color = Color.Unspecified,
    val unfocusedLeadingContentColor: Color = Color.Unspecified,
    val trailingContentColor: Color = Color.Unspecified,
    val unfocusedTrailingContentColor: Color = Color.Unspecified,
    val verticalDividerLineColor: Color = Color.Unspecified,
)

internal val LocalTextFieldColorsProvider = compositionLocalOf { TextFieldColors() }

@Composable
internal fun ProvideTextFieldColors(
    colors: TextFieldColors,
    content: @Composable () -> Unit,
) = CompositionLocalProvider(LocalTextFieldColorsProvider provides colors, content = content)

internal fun componentColors(invertedColors: Boolean) =
    when (invertedColors) {
        true ->
            TextFieldColors(
                labelColor = MivaColors.Black,
                placeholderColor = MivaColors.Secondary.Blue200,
                cursorColor = MivaColors.Primary.MainBlue,
                errorColor = MivaColors.ErrorRed,
                footerTextColor = MivaColors.Secondary.NeutralThree,
                selectionBackgroundColor = MivaColors.Secondary.NeutralSix,
                focusedIndicatorColor = MivaColors.Primary.MainBlue,
                unfocusedIndicatorColor = MivaColors.Secondary.NeutralSixPointFive,
            )
        false ->
            TextFieldColors(
                labelColor = MivaColors.Black,
                placeholderColor = MivaColors.Secondary.NeutralFive,
                cursorColor = MivaColors.Black,
                errorColor = MivaColors.ErrorRed,
                selectionBackgroundColor = MivaColors.Secondary.NeutralSix,
                footerTextColor = MivaColors.Secondary.NeutralThree,
                focusedIndicatorColor = MivaColors.Primary.MainBlue,
                unfocusedIndicatorColor = MivaColors.Secondary.NeutralSixPointFive,
            )
    }
