package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.components.divider.Divider
import university.miva.designsystem.components.image.ClickableSpanImage
import university.miva.designsystem.components.sheets.AppBottomSheet
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.R as DesignSystemR

@Composable
fun GuestSignInView(
    visible: Boolean,
    onDismiss: () -> Unit,
    onEmailSignInClick: () -> Unit,
) {
    AppBottomSheet(
        visible = visible,
        onFinalDismiss = onDismiss,
        dragHandle = null,
        content = {
            Surface(
                shape = RoundedCornerShape(topStart = Theme.spacing.x4, topEnd = Theme.spacing.x4),
                color = Color.White,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
            ) {
                Column(
                    modifier =
                        Modifier
                            .padding(bottom = 26.dp),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Theme.spacing.x6, vertical = Theme.spacing.x1),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        ClickableSpanImage(
                            image = R.drawable.ic_close,
                            contentDescription = stringResource(R.string.close),
                        ) {
                            onDismiss.invoke()
                        }
                    }
                    Divider(color = MivaColors.Secondary.DividerColor)
                    Spacer(modifier = Modifier.height(Theme.spacing.x2))
                    Text(
                        text = stringResource(DesignSystemR.string.access_your_student_account),
                        modifier = Modifier.padding(horizontal = Theme.spacing.x4, vertical = Theme.spacing.x4),
                        style =
                            Theme.typography.h3.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                        textAlign = TextAlign.Start,
                    )

                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style =
                                    SpanStyle(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        fontFamily =
                                            FontFamily(
                                                Font(DesignSystemR.font.manrope_medium, FontWeight.Medium),
                                            ),
                                        color = MivaColors.TextColors.Blue300,
                                    ),
                            ) {
                                append(stringResource(DesignSystemR.string.Are_you_a_current))
                            }
                            withStyle(
                                style =
                                    SpanStyle(
                                        fontSize = 16.sp,
                                        fontFamily =
                                            FontFamily(
                                                Font(DesignSystemR.font.manrope_medium, FontWeight.Medium),
                                            ),
                                        fontWeight = FontWeight.Bold,
                                        color = MivaColors.TextColors.BlueDeep,
                                    ),
                            ) {
                                append(" ${stringResource(DesignSystemR.string.miva_student)} ")
                            }
                            withStyle(
                                style =
                                    SpanStyle(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        fontFamily =
                                            FontFamily(
                                                Font(DesignSystemR.font.manrope_medium, FontWeight.Medium),
                                            ),
                                        color = MivaColors.TextColors.Blue300,
                                    ),
                            ) {
                                append(stringResource(DesignSystemR.string.or_have_you))
                            }

                            withStyle(
                                style =
                                    SpanStyle(
                                        fontSize = 16.sp,
                                        fontFamily =
                                            FontFamily(
                                                Font(DesignSystemR.font.manrope_medium, FontWeight.Medium),
                                            ),
                                        fontWeight = FontWeight.Bold,
                                        color = MivaColors.TextColors.BlueDeep,
                                    ),
                            ) {
                                append(" ${stringResource(DesignSystemR.string.submitted_an_application)} ")
                            }
                            withStyle(
                                style =
                                    SpanStyle(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        fontFamily =
                                            FontFamily(
                                                Font(DesignSystemR.font.manrope_medium, FontWeight.Medium),
                                            ),
                                        color = MivaColors.TextColors.Blue300,
                                    ),
                            ) {
                                append(stringResource(DesignSystemR.string.login_to_access_your))
                            }
                        },
                        modifier = Modifier.padding(horizontal = Theme.spacing.x4),
                        textAlign = TextAlign.Start,
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.x6))

                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Theme.spacing.x4)
                                .clip(RoundedCornerShape(Theme.spacing.x3))
                                .background(MivaColors.Secondary.Grey50)
                                .clickable { onEmailSignInClick() }
                                .padding(Theme.spacing.x4),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = DesignSystemR.drawable.ic_email),
                            contentDescription = stringResource(DesignSystemR.string.email_icon),
                        )
                        Spacer(modifier = Modifier.width(Theme.spacing.x2))
                        Text(
                            stringResource(DesignSystemR.string.sign_in_with_your_email),
                            style =
                                Theme.typography.bodyM.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MivaColors.TextColors.BlueDeep,
                                ),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(id = DesignSystemR.drawable.ic_outline_arrow_right),
                            contentDescription = stringResource(DesignSystemR.string.arrow_right_icon),
                        )
                    }
                }
            }
        },
    )
}
