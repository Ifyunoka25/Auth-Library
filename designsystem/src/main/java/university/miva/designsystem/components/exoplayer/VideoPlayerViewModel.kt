package university.miva.designsystem.components.exoplayer

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import university.miva.designsystem.util.FileUtils.decryptToTempForPlayback
import university.miva.designsystem.util.FileUtils.encFile
import java.io.File

data class PlayerUiState(
    val preparingOffline: Boolean = false,
)

class VideoPlayerViewModel(
    private val activity: Activity,
    private val videoUrl: String,
) : ViewModel() {
    var isFullScreen by mutableStateOf(false)
        private set

    private var tempDecrypted: File? = null

    private val _ui = MutableStateFlow(PlayerUiState())
    val ui: StateFlow<PlayerUiState> = _ui.asStateFlow()

    val exoPlayer: ExoPlayer = ExoPlayer.Builder(activity).build()

    private var lastPosition: Long = 0L
    private var wasPlaying: Boolean = false

    fun preferOfflineIfAvailable(
        context: Context,
        baseName: String,
    ) {
        val enc = encFile(context, baseName)
        if (!enc.exists()) {
            playOnline()
            return
        }

        viewModelScope.launch {
            _ui.value = PlayerUiState(preparingOffline = true)
            val temp =
                withContext(Dispatchers.IO) {
                    decryptToTempForPlayback(context, enc)
                }
            tempDecrypted = temp
            setAndPlay(MediaItem.fromUri(Uri.fromFile(temp)))
            _ui.value = PlayerUiState(preparingOffline = false)
        }
    }

    private fun setAndPlay(item: MediaItem) {
        exoPlayer.setMediaItem(item)
        exoPlayer.prepare()
        exoPlayer.seekTo(lastPosition)
        exoPlayer.playWhenReady = true
    }

    fun toggleFullScreen() {
        isFullScreen = !isFullScreen
        Timber.tag("VideoPlayerViewModel").d("toggleFullScreen isFullScreen: $isFullScreen")
        toggleOrientation()
    }

    private fun toggleOrientation() {
        Timber.tag("VideoPlayerViewModel").d("toggleOrientation start: isFullScreen=$isFullScreen")
        activity.requestedOrientation =
            if (isFullScreen) {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        Timber.tag("VideoPlayerViewModel").d("toggleOrientation done: isFullScreen=$isFullScreen")
    }

    fun saveState() {
        lastPosition = exoPlayer.currentPosition
        wasPlaying = exoPlayer.isPlaying
        exoPlayer.pause()
        Timber.tag("VideoPlayerViewModel").d("saveState wasPlaying=$wasPlaying")
        Timber.tag("VideoPlayerViewModel").d("saveState exoPlayer.isPlaying=${exoPlayer.isPlaying}")
    }

    fun restoreState() {
        exoPlayer.seekTo(lastPosition)
        exoPlayer.playWhenReady = wasPlaying
        Timber.tag("VideoPlayerViewModel").d("restoreState wasPlaying=$wasPlaying")
        Timber.tag("VideoPlayerViewModel").d("restoreState exoPlayer.isPlaying=${exoPlayer.isPlaying}")
    }

    /** Switch back to streaming URL */
    fun playOnline() {
        cleanupTemp()
        setAndPlay(MediaItem.fromUri(videoUrl))
    }

    private fun cleanupTemp() {
        try {
            tempDecrypted?.let { if (it.exists()) it.delete() }
        } catch (_: Throwable) {
        }
        tempDecrypted = null
    }

    fun exitFullScreen() {
        isFullScreen = false
        toggleOrientation()
    }

    override fun onCleared() {
        super.onCleared()
        cleanupTemp()
        exoPlayer.release()
    }
}
