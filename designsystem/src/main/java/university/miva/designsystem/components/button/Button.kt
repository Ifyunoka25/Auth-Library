package university.miva.designsystem.components.button

import Theme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import university.miva.designsystem.R
import university.miva.designsystem.components.button.internal.ButtonColors
import university.miva.designsystem.components.button.internal.LocalButtonColorProvider
import university.miva.designsystem.components.button.internal.ProvideButtonColors
import university.miva.designsystem.components.button.internal.toButtonColors
import university.miva.designsystem.components.button.model.ButtonModel
import university.miva.designsystem.components.button.model.ButtonState
import university.miva.designsystem.components.button.model.ButtonStyle
import university.miva.designsystem.components.button.model.toTextStyle
import university.miva.designsystem.components.text.BasicText
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.theme.isSystemInDarkTheme

@Composable
fun SmallDefaultButton(
    model: ButtonModel,
    modifier: Modifier = Modifier,
    state: ButtonState = ButtonState.Enabled,
    onClick: () -> Unit,
    style: ButtonStyle = ButtonStyle.Small,
    invertedColors: Boolean = isSystemInDarkTheme(),
) {
    DefaultButton(
        model = model,
        modifier = modifier,
        state = state,
        style = style,
        onClick = onClick,
        invertedColors = invertedColors,
    )
}

@Composable
fun DefaultButton(
    model: ButtonModel,
    modifier: Modifier = Modifier,
    style: ButtonStyle = ButtonStyle.Regular,
    state: ButtonState = ButtonState.Enabled,
    onClick: () -> Unit,
    invertedColors: Boolean = isSystemInDarkTheme(),
) {
    val isEnabled = remember(state) { state == ButtonState.Enabled }
    ProvideButtonColors(colors = componentColors(invertedColors = invertedColors)) {
        Button(
            onClick = { onClick.invoke() },
            modifier = modifier,
            enabled = isEnabled,
            shape = Theme.shape.buttonShape,
            colors = LocalButtonColorProvider.current.toButtonColors(),
            contentPadding = PaddingValues(Theme.spacing.x4),
            elevation =
                ButtonDefaults.buttonElevation(
                    defaultElevation = 0.5.dp,
                    pressedElevation = 1.0.dp,
                    hoveredElevation = 1.5.dp,
                    focusedElevation = 1.5.dp,
                ),
        ) {
            BasicText(
                text = model.title,
                style = style.toTextStyle(isEnabled = isEnabled),
            )
        }
    }
}

@Composable
private fun componentColors(invertedColors: Boolean) =
    when (invertedColors) {
        true ->
            ButtonColors(
                backgroundColor = MivaColors.Primary.MainBlue,
                textColor = MivaColors.White,
            )

        false ->
            ButtonColors(
                backgroundColor = MivaColors.Primary.MainBlue,
                disabledBackgroundColor = MivaColors.Secondary.Gray600,
                textColor = MivaColors.White,
                disabledTextColor = MivaColors.White,
            )
    }

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = MivaColors.TextColors.White,
    onClick: () -> Unit,
    borderStroke: BorderStroke? = null,
    colors: Color = MivaColors.Secondary.Sent,
    horizontalPadding: Dp = Theme.spacing.x5,
    verticalPadding: Dp = Theme.spacing.x5,
) {
    Button(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding, vertical = verticalPadding)
                .height(52.dp),
        shape = RoundedCornerShape(Theme.spacing.x2),
        border = borderStroke,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = colors,
            ),
    ) {
        Text(
            title,
            style =
                Theme.typography.h3.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = titleColor,
                ),
        )
    }
}

@Composable
fun PrevActivityButton(
    modifier: Modifier = Modifier,
    itemColor: Color = MivaColors.Secondary.BlueDeep,
    title: String? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .height(Theme.spacing.x10)
                .background(color = MivaColors.Secondary.GrayDivider300, shape = RoundedCornerShape(Theme.spacing.x2))
                .clickable { onClick() }
                .padding(start = 10.dp, end = 18.dp, top = Theme.spacing.x2, bottom = Theme.spacing.x2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            painterResource(id = R.drawable.ic_angle_left),
            contentDescription = null,
            colorFilter = ColorFilter.tint(itemColor),
        )
        Spacer(modifier = Modifier.width(Theme.spacing.x2))

        BasicText(
            text = title ?: stringResource(R.string.prev_activity),
            style =
                Theme.typography.bodyM.copy(
                    color = itemColor,
                ),
        )
    }
}

@Composable
fun NextActivityButton(
    modifier: Modifier = Modifier,
    title: String? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color = MivaColors.Secondary.GrayDivider300, shape = RoundedCornerShape(Theme.spacing.x2))
                .clickable { onClick() }
                .padding(start = 18.dp, end = 10.dp, top = Theme.spacing.x2, bottom = Theme.spacing.x2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        BasicText(
            text = title ?: stringResource(R.string.next_activity),
            style = Theme.typography.bodyM,
        )

        Spacer(modifier = Modifier.width(Theme.spacing.x2))
        Image(
            painterResource(id = R.drawable.ic_angle_right),
            contentDescription = null,
        )
    }
}

@Composable
fun AppBackButton(
    onClick: () -> Unit,
    paddingValues: Dp = Theme.spacing.x3,
    tint: Color = MivaColors.Primary.MainBlue,
) {
    Image(
        painterResource(id = R.drawable.ic_back_arrow),
        contentDescription = stringResource(R.string.back_icon),
        colorFilter = ColorFilter.tint(tint),
        modifier =
            Modifier
                .clip(shape = CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication =
                        ripple(
                            color = MivaColors.Secondary.Blue200,
                        ),
                ) {
                    onClick()
                }.padding(paddingValues),
    )
}

@Composable
fun IconLeftButton(
    modifier: Modifier = Modifier,
    itemColor: Color = MivaColors.Secondary.BlueDeep,
    backgroundColor: Color = MivaColors.Secondary.GrayDivider300,
    title: String,
    icon: Int? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .height(50.dp)
                .background(color = backgroundColor, shape = RoundedCornerShape(Theme.spacing.x2))
                .clickable { onClick() }
                .padding(Theme.spacing.x3),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        if (icon != null) {
            Image(
                painterResource(id = icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(itemColor),
            )
        }
        Spacer(modifier = Modifier.width(Theme.spacing.x2))

        BasicText(
            text = title,
            style =
                Theme.typography.bodyM.copy(
                    color = itemColor,
                ),
        )
    }
}

@Composable
fun IconRightButton(
    modifier: Modifier = Modifier,
    itemColor: Color = MivaColors.Secondary.BlueDeep,
    backgroundColor: Color = MivaColors.Secondary.GrayDivider300,
    title: String,
    buttonHeight: Dp = 50.dp,
    icon: Int? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .height(buttonHeight)
                .background(color = backgroundColor, shape = RoundedCornerShape(Theme.spacing.x2))
                .clickable { onClick() }
                .padding(Theme.spacing.x3),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        BasicText(
            text = title,
            style =
                Theme.typography.bodyS.copy(
                    color = itemColor,
                    fontWeight = FontWeight.SemiBold,
                ),
        )
        Spacer(modifier = Modifier.width(Theme.spacing.x2))
        if (icon != null) {
            Image(
                painterResource(id = icon),
                contentDescription = null,
                colorFilter = ColorFilter.tint(itemColor),
            )
        }
    }
}

@Composable
fun ActionButtons(
    modifier: Modifier = Modifier,
    modifierLeft: Modifier = Modifier,
    modifierRight: Modifier = Modifier,
    leftButtonText: String = "",
    rightButtonText: String = "",
    onLeftButtonClick: () -> Unit = {},
    onRightButtonClick: () -> Unit = {},
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.spacing.x4),
    ) {
        Button(
            onClick = onLeftButtonClick,
            modifier = modifierLeft.weight(1f),
            shape = RoundedCornerShape(Theme.spacing.x2),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MivaColors.Secondary.Gray300,
                ),
            elevation = ButtonDefaults.buttonElevation(0.dp),
        ) {
            BasicText(
                leftButtonText,
                style =
                    Theme.typography.bodyM.copy(
                        lineHeight = 24.sp,
                    ),
                modifier = modifierLeft.padding(vertical = Theme.spacing.x2),
            )
        }
        Spacer(modifier = Modifier.width(Theme.spacing.x5))
        Button(
            onClick = onRightButtonClick,
            modifier = modifierRight.weight(1f),
            shape = RoundedCornerShape(Theme.spacing.x2),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = MivaColors.Secondary.BlueDeep,
                ),
            elevation = ButtonDefaults.buttonElevation(0.dp),
        ) {
            BasicText(
                rightButtonText,
                style =
                    Theme.typography.bodyM.copy(
                        lineHeight = 24.sp,
                        color = MivaColors.White,
                    ),
                modifier = modifierRight.padding(vertical = Theme.spacing.x2),
            )
        }
    }
}

@Composable
fun PreviousNextButtons(
    onClickPreviousButtonClick: () -> Unit,
    onClickNextButtonClick: () -> Unit,
) {
    Column {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = Theme.spacing.x6),
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.x4),
        ) {
            PrevActivityButton(
                onClick = onClickPreviousButtonClick,
                modifier = Modifier.weight(1f),
            )
            NextActivityButton(
                onClick = onClickNextButtonClick,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
fun PillButton(
    title: String? = null,
    onClick: () -> Unit,
    icon: Painter? = null,
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color = MivaColors.White,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(50),
) {
    val isIconOnly = icon != null && title == null

    Button(
        onClick = onClick,
        modifier = modifier.then(
            if (isIconOnly) Modifier.sizeIn(minWidth = 40.dp, minHeight = 40.dp)
            else Modifier
        ),
        enabled = enabled,
        shape = shape,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            disabledContentColor = contentColor.copy(alpha = 0.7f),
        ),
    ) {
        if (isIconOnly) {
            Box(
                modifier = Modifier
                    .width(65.dp)
                    .height(40.dp)
                    .padding(Theme.spacing.x2),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MivaColors.White,
                    modifier = Modifier.size(Theme.spacing.x5),
                )
            }
        } else {
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MivaColors.White,
                    modifier = Modifier.size(Theme.spacing.x6),
                )
            }
            if (!title.isNullOrEmpty()) {
                Text(
                    text = title,
                    style = Theme.typography.bodyXs.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = contentColor,
                    ),
                    modifier = Modifier.padding(
                        vertical = Theme.spacing.x3,
                        horizontal = Theme.spacing.x4,
                    ),
                )
            }
        }
    }
}

@Composable
fun SmallButton(
    title: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color,
    textColor: Color = MivaColors.White,
) {
    Row(
        modifier =
            modifier
                .background(color = containerColor, shape = RoundedCornerShape(Theme.spacing.x2))
                .clickable { onClick() }
                .padding(horizontal = Theme.spacing.x3, vertical = Theme.spacing.x2),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        BasicText(
            title.toString(),
            style =
                Theme.typography.bodyXs.copy(
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor,
                ),
        )
    }
}
