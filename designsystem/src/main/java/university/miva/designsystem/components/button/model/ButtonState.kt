package university.miva.designsystem.components.button.model

import Theme
import androidx.compose.runtime.Composable
import university.miva.designsystem.components.button.internal.LocalButtonColorProvider
import university.miva.designsystem.components.button.model.ButtonStyle.Regular
import university.miva.designsystem.components.button.model.ButtonStyle.Small

enum class ButtonState {
    Enabled,
    Disabled,
    Loading,
}

fun Boolean.asButtonState() =
    when (this) {
        true -> ButtonState.Enabled
        false -> ButtonState.Disabled
    }

enum class ButtonStyle {
    Regular,
    Small,
}

@Composable
internal fun ButtonStyle.toTextStyle(isEnabled: Boolean) =
    when (this) {
        Regular ->
            Theme.typography.h4.copy(
                color =
                    if (isEnabled) {
                        LocalButtonColorProvider.current.textColor
                    } else {
                        LocalButtonColorProvider.current.disabledTextColor
                    },
            )

        Small ->
            Theme.typography.headingS.copy(
                color =
                    if (isEnabled) {
                        LocalButtonColorProvider.current.textColor
                    } else {
                        LocalButtonColorProvider.current.disabledTextColor
                    },
            )
    }

@Composable
internal fun Boolean.toIconColor() =
    when (this) {
        true -> {
            LocalButtonColorProvider.current.contentColor
        }

        false -> {
            LocalButtonColorProvider.current.disabledContentColor
        }
    }
