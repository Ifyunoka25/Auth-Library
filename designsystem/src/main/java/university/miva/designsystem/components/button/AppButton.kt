package university.miva.designsystem.components.button

import Theme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.theme.MivaColors

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    textColor: Color,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight? = FontWeight.Medium,
    enabled: Boolean = true,
    shouldAddBtnHeight: Boolean = true,
    onClick: () -> Unit,
) {
    val rippleColor =
        if (backgroundColor.luminance() > 0.5f) {
            MivaColors.Secondary.BlueDeep
        } else {
            Color.White
        }

    CompositionLocalProvider(
        LocalRippleConfiguration provides RippleConfiguration(color = rippleColor),
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
            shape = RoundedCornerShape(Theme.spacing.x2),
            enabled = enabled,
            modifier =
                modifier
                    .fillMaxWidth()
                    .then(if (shouldAddBtnHeight) Modifier.height(Theme.spacing.x12) else Modifier),
            contentPadding =
                PaddingValues(
                    horizontal = Theme.spacing.x2,
                    vertical = Theme.spacing.x2,
                ),
        ) {
            Text(
                text = text,
                color = textColor,
                style =
                    Theme.typography.bodyM.copy(
                        fontSize = fontSize,
                        fontWeight = fontWeight,
                    ),
            )
        }
    }
}

@Composable
fun AppOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    borderColor: Color,
    textColor: Color,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight? = FontWeight.Medium,
    enabled: Boolean = true,
    shouldAddBtnHeight: Boolean = true,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .then(if (shouldAddBtnHeight) Modifier.height(Theme.spacing.x12) else Modifier),
        colors =
            ButtonDefaults.outlinedButtonColors(
                contentColor = textColor,
                containerColor = Color.Transparent,
            ),
        border =
            ButtonDefaults.outlinedButtonBorder().copy(
                width = 1.dp,
                brush =
                    androidx.compose.ui.graphics
                        .SolidColor(borderColor),
            ),
        shape = Theme.shape.buttonShape,
        enabled = enabled,
        contentPadding =
            PaddingValues(
                horizontal = Theme.spacing.x3,
                vertical = Theme.spacing.x2,
            ),
    ) {
        Text(
            text = text,
            style =
                Theme.typography.bodyM.copy(
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                ),
            color = textColor,
        )
    }
}
