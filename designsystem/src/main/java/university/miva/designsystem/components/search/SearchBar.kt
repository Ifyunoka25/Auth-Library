package university.miva.designsystem.components.search

import Theme
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import university.miva.designsystem.R
import university.miva.designsystem.components.image.ClickableSpanImage
import university.miva.designsystem.components.image.Image
import university.miva.designsystem.theme.MivaColors

@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    hintText: String,
    @DrawableRes image: Int? = null,
    showBorder: Boolean = false,
    onQueryChange: (String) -> Unit,
    onLeadingIconClick: () -> Unit,
    onClick: () -> Unit,
    onSearch: () -> Unit = {},
    focusedContainerColor: Color? = null,
    unfocusedContainerColor: Color? = null,
    hintTextColor: Color? = null,
    textColor: Color? = null,
    readOnly: Boolean = false,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            readOnly = readOnly,
            placeholder = {
                Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                    Text(
                        hintText,
                        style = Theme.typography.bodyM.copy(color = hintTextColor ?: MivaColors.TextColors.Blue300),
                    )
                }
            },
            leadingIcon = {
                ClickableSpanImage(
                    tint = MivaColors.TextColors.Blue300,
                    image = image ?: R.drawable.ic_search,
                    contentDescription = stringResource(R.string.search),
                ) {
                    onLeadingIconClick()
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions =
                KeyboardActions(
                    onSearch = {
                        onSearch()
                    },
                ),
            singleLine = true,
            textStyle = Theme.typography.bodyM.copy(color = textColor ?: MivaColors.TextColors.Blue300),
            shape = RoundedCornerShape(100.dp),
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(Theme.spacing.x14)
                    .then(
                        if (showBorder) {
                            Modifier.border(
                                width = 1.dp,
                                color = MivaColors.Secondary.GrayDivider300,
                                shape = RoundedCornerShape(100.dp),
                            )
                        } else {
                            Modifier
                        },
                    ),
            colors =
                TextFieldDefaults.colors(
                    focusedContainerColor = focusedContainerColor ?: MivaColors.White,
                    unfocusedContainerColor = unfocusedContainerColor ?: MivaColors.White,
                    focusedIndicatorColor = MivaColors.Secondary.Transparent,
                    unfocusedIndicatorColor = MivaColors.Secondary.Transparent,
                    disabledIndicatorColor = MivaColors.Secondary.Transparent,
                ),
        )

        if (readOnly) {
            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(50.dp))
                        .clickable { onClick() },
            )
        }
    }
}

@Composable
fun AppChatInputBar(
    modifier: Modifier = Modifier,
    query: String,
    hintText: String,
    @DrawableRes image: Int? = null,
    showBorder: Boolean = false,
    onQueryChange: (String) -> Unit,
    onLeadingIconClick: () -> Unit,
    onClick: () -> Unit,
    focusedContainerColor: Color? = null,
    unfocusedContainerColor: Color? = null,
    hintTextColor: Color? = null,
    textColor: Color? = null,
    readOnly: Boolean = false,
    showSendBtn: Boolean = false,
    isLoading: Boolean = false,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            readOnly = readOnly,
            placeholder = {
                Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                    Text(
                        hintText,
                        style = Theme.typography.bodyM.copy(color = hintTextColor ?: MivaColors.TextColors.Blue300),
                    )
                }
            },
            trailingIcon = {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            color = MivaColors.Secondary.BlueDeep,
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                        )
                    }
                    showSendBtn -> {
                        ClickableSpanImage(
                            image = image ?: R.drawable.ic_input_reply,
                            contentDescription = stringResource(R.string.reply),
                            onClick = onLeadingIconClick,
                        )
                    }
                }
            },
            singleLine = true,
            textStyle = Theme.typography.bodyM.copy(color = textColor ?: MivaColors.TextColors.Blue300),
            shape = RoundedCornerShape(50.dp),
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(Theme.spacing.x14)
                    .then(
                        if (showBorder) {
                            Modifier.border(
                                width = 1.dp,
                                color = MivaColors.Secondary.GrayDivider300,
                                shape = RoundedCornerShape(50.dp),
                            )
                        } else {
                            Modifier
                        },
                    ),
            colors =
                TextFieldDefaults.colors(
                    focusedContainerColor = focusedContainerColor ?: MivaColors.White,
                    unfocusedContainerColor = unfocusedContainerColor ?: MivaColors.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
        )

        if (readOnly) {
            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .padding(horizontal = Theme.spacing.x4)
                        .clip(RoundedCornerShape(50.dp))
                        .clickable { onClick() },
            )
        }
    }
}

@Composable
fun ExpandingChatInput(
    value: String,
    hintText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes image: Int? = null,
    onFocusChanged: ((Boolean) -> Unit)? = null,
    onPressedChanged: ((Boolean) -> Unit)? = null,
    focusState: Boolean = false,
    onSendClick: () -> Unit,
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

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(50.dp))
                .padding(start = Theme.spacing.x4, end = Theme.spacing.x4, bottom = Theme.spacing.x1),
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = Theme.typography.bodyM.copy(color = MivaColors.TextColors.BlueDeep),
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .heightIn(min = Theme.spacing.x10, max = 150.dp)
                    .align(Alignment.CenterVertically),
            decorationBox = { innerTextField ->
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = Theme.spacing.x4, horizontal = 0.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hintText,
                            color = MivaColors.TextColors.Blue300.copy(alpha = 0.5f),
                            style = Theme.typography.bodyM,
                        )
                    }
                    innerTextField()
                }
            },
            maxLines = Int.MAX_VALUE,
            singleLine = false,
        )
        Spacer(modifier = Modifier.width(Theme.spacing.x2))
        Image(
            image = image ?: R.drawable.ic_input_reply,
            contentDescription = stringResource(R.string.reply),
            modifier =
                Modifier
                    .size(
                        Theme.spacing.x9,
                    ).clip(CircleShape)
                    .clickable { if (value.isNotEmpty()) onSendClick() },
        )
    }
}

@Composable
@Preview
fun PreviewSearch() {
    AppSearchBar(
        query = "Search",
        onQueryChange = {},
        hintText = "Search",
        image = 0,
        onLeadingIconClick = {},
        onClick = {},
        onSearch = {},
    )
}
