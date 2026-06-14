package university.miva.designsystem.components.button.model

import android.media.Image
import androidx.compose.runtime.Immutable

@Immutable
data class ButtonModel(
    val title: String,
    val leadingImage: Image? = null,
    val trailingImage: Image? = null,
)
