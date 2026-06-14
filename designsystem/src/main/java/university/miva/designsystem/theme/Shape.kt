package university.miva.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape

@Immutable
data class Shape(
    val buttonShape: Shape,
    val roundShape: Shape,
    val chipShape: Shape,
    val imageShape: Shape,
    val circleShape: Shape,
    val textShape: Shape,
    val card: Shape,
    val receiverChatBubble: Shape,
    val senderChatBubble: Shape,
)
