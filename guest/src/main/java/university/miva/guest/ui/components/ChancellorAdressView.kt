package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.components.image.ClickableSpanImage
import university.miva.designsystem.components.sheets.AppBottomSheet
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.R as DesignSystemR

@Composable
fun ChancellorAddressView(
    visible: Boolean,
    onDismiss: () -> Unit,
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
                LazyColumn {
                    item {
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = Theme.spacing.x4),
                            horizontalArrangement = Arrangement.Absolute.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = stringResource(DesignSystemR.string.chancellors_address_),
                                style =
                                    Theme.typography.h3.copy(
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MivaColors.TextColors.BlueDeep,
                                    ),
                                textAlign = TextAlign.Start,
                            )
                            Spacer(modifier = Modifier.width(Theme.spacing.x6))
                            ClickableSpanImage(
                                image = R.drawable.ic_close,
                                contentDescription = stringResource(R.string.close),
                                tint = MivaColors.TextColors.BlueDeep,
                            ) {
                                onDismiss()
                            }
                        }
                        HorizontalDivider(color = MivaColors.Secondary.DividerColor)
                        Spacer(modifier = Modifier.height(Theme.spacing.x6))
                        Image(
                            painter = painterResource(id = DesignSystemR.drawable.ic_chancellor),
                            contentDescription = stringResource(DesignSystemR.string.email_icon),
                            modifier = Modifier.padding(horizontal = Theme.spacing.x4),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.height(Theme.spacing.x6))
                        Text(
                            text = stringResource(DesignSystemR.string.chancellors_message_start),
                            modifier = Modifier.padding(horizontal = Theme.spacing.x4),
                            style =
                                Theme.typography.bodyM.copy(
                                    lineHeight = 24.sp,
                                ),
                            textAlign = TextAlign.Start,
                        )
                        Spacer(modifier = Modifier.height(Theme.spacing.x4))
                        Text(
                            text = stringResource(DesignSystemR.string.chancellors_message_end),
                            modifier = Modifier.padding(horizontal = Theme.spacing.x4),
                            style =
                                Theme.typography.bodyM.copy(
                                    lineHeight = 24.sp,
                                ),
                            textAlign = TextAlign.Start,
                        )

                        Spacer(modifier = Modifier.height(Theme.spacing.x6))
                        Image(
                            painter = painterResource(id = DesignSystemR.drawable.ic_chancellor_signature),
                            contentDescription = stringResource(DesignSystemR.string.chancellor_signature),
                            modifier = Modifier.padding(horizontal = Theme.spacing.x4),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                }
            }
        },
    )
}
