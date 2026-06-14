package university.miva.designsystem.theme

import Theme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.theme.internal.TextStyleColors

@Composable
fun MivaTheme(
    invertedColors: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val mivaColor =
        Colors(
            surface =
                Surface(
                    primaryDark = MivaColors.Black,
                    primaryLight = MivaColors.White,
                    secondaryDark = MivaColors.Black,
                    secondaryLight = MivaColors.White,
                ),
            surfaceInteractive =
                SurfaceInteractive(
                    enabledOnDark = MivaColors.White,
                    enabledOnLight = MivaColors.Black,
                    disabledOnDark = MivaColors.Secondary.NeutralSix,
                    disabledOnLight = MivaColors.Secondary.NeutralSix,
                    hoverTapOnDark = MivaColors.Secondary.NeutralFour,
                    hoverTapOnLight = MivaColors.Secondary.NeutralFour,
                ),
            border =
                Border(
                    primaryOnDark = MivaColors.Secondary.NeutralFour,
                    primaryOnLight = MivaColors.Secondary.NeutralFour,
                    secondaryOnDark = MivaColors.Secondary.NeutralSix,
                    secondaryOnLight = MivaColors.Secondary.NeutralSix,
                ),
            element =
                Element(
                    primaryOnDark = MivaColors.White,
                    primaryOnLight = MivaColors.Secondary.BlueDeep,
                    secondaryOnDark = MivaColors.White,
                    secondaryOnLight = MivaColors.Black,
                ),
            elementInteractive =
                ElementInteractive(
                    enabledOnDark = MivaColors.White,
                    enabledOnLight = MivaColors.Primary.Black,
                    disabledOnDark = MivaColors.White.copy(alpha = 0.1f),
                    disabledOnLight = MivaColors.White,
                    hoverTapOnDark = MivaColors.White.copy(alpha = 0.6f),
                    hoverTapOnLight = MivaColors.Primary.Black.copy(alpha = 0.8f),
                ),
            shadow =
                Shadow(
                    spotOnDark = MivaColors.Shadow.lightSpot,
                    spotOnLight = MivaColors.Shadow.darkSpot,
                ),
        )

    val manRopeFontFamily =
        FontFamily(
            Font(R.font.manrope, FontWeight.Normal),
            Font(R.font.manrope_medium, FontWeight.Medium),
            Font(R.font.manrope_bold, FontWeight.Bold),
        )

    val cinzelFontFamily =
        FontFamily(
            Font(R.font.cinzel_regular, FontWeight.Normal),
            Font(R.font.cinzel_regular, FontWeight.Medium),
            Font(R.font.cinzel_bold, FontWeight.Bold),
            Font(R.font.cinzel_black, FontWeight.Black),
        )

    val typography =
        Typography(
            h1 =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    color = MivaColors.TextColors.BlueDeep,
                ),
            h2 =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 32.sp,
                    color = mivaColor.element.primaryOnLight,
                ),
            h3 =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                    color = mivaColor.element.primaryOnLight,
                ),
            h4 =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = mivaColor.element.primaryOnLight,
                ),
            headingS =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = mivaColor.element.primaryOnLight,
                ),
            headingM =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = mivaColor.element.primaryOnLight,
                ),
            body =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = mivaColor.element.primaryOnLight,
                ),
            bodyM =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = mivaColor.element.primaryOnLight,
                ),
            bodyS =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = mivaColor.element.primaryOnLight,
                ),
            bodyXs =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = mivaColor.element.primaryOnLight,
                ),
            labelS =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = mivaColor.element.primaryOnLight,
                ),
            labelXs =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 10.sp,
                    color = mivaColor.element.primaryOnLight,
                ),
            chips =
                TextStyle(
                    fontFamily = manRopeFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                ),
        )

    val shape =
        Shape(
            buttonShape = RoundedCornerShape(Theme.spacing.x2),
            circleShape = RoundedCornerShape(50),
            roundShape = RoundedCornerShape(Theme.spacing.x3),
            imageShape = CircleShape,
            card = RoundedCornerShape(Theme.spacing.x2),
            textShape = RoundedCornerShape(Theme.spacing.x1),
            chipShape = RoundedCornerShape(Theme.spacing.x2),
            senderChatBubble =
                RoundedCornerShape(
                    topStart = Theme.spacing.x2,
                    bottomStart = Theme.spacing.x2,
                    bottomEnd = Theme.spacing.x2,
                ),
            receiverChatBubble =
                RoundedCornerShape(
                    topStart = Theme.spacing.x2,
                    topEnd = Theme.spacing.x2,
                    bottomEnd = Theme.spacing.x2,
                ),
        )

    MaterialTheme(
        shapes = androidx.compose.material3.Shapes(),
    ) {
        CompositionLocalProvider(
            LocalRippleConfiguration provides
                RippleConfiguration(
                    color = MivaColors.Secondary.Blue200,
                ),
        ) {
            WithTheme(
                colors = mivaColor,
                typography = typography,
                shape = shape,
                clauseStyleColors = provideClauseColors(invertedColors),
                content = content,
            )
        }
    }
}

/**
 * Enforce the light mode
 */
@Composable
fun isSystemInDarkTheme() = false

@Composable
private fun provideClauseColors(invertedColors: Boolean) =
    when (invertedColors) {
        true ->
            TextStyleColors(
                regular = MivaColors.Secondary.NeutralZero,
                caution = MivaColors.Secondary.NeutralFour,
                success = MivaColors.Primary.Black,
                error = MivaColors.ErrorRed,
            )

        false ->
            TextStyleColors(
                regular = MivaColors.Secondary.NeutralZero,
                caution = MivaColors.Secondary.NeutralFour,
                success = MivaColors.Primary.Black,
                error = MivaColors.ErrorRed,
            )
    }
