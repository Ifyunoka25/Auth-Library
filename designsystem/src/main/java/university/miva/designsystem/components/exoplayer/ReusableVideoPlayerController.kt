package university.miva.designsystem.components.exoplayer

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

object ReusableVideoPlayerController {
    private var exoPlayer: ExoPlayer? = null
    private var currentVideoUrl: String? = null

    private var lastPosition: Long = 0L
    private var wasPlaying: Boolean = false

    fun getPlayer(
        context: Context,
        videoUrl: String,
    ): ExoPlayer {
        if (exoPlayer != null && currentVideoUrl == videoUrl) {
            return exoPlayer!!
        }

        exoPlayer?.release()

        currentVideoUrl = videoUrl
        return ExoPlayer
            .Builder(context)
            .build()
            .apply {
                setMediaItem(MediaItem.fromUri(videoUrl))
                prepare()
                playWhenReady = false
            }.also {
                exoPlayer = it
            }
    }

    fun saveState() {
        exoPlayer?.let {
            lastPosition = it.currentPosition
            wasPlaying = it.isPlaying
            it.pause()
        }
    }

    fun restoreState() {
        exoPlayer?.let {
            it.seekTo(lastPosition)
            it.playWhenReady = true
        }
    }

    fun pause() {
        exoPlayer?.pause()
    }

    fun release() {
        exoPlayer?.release()
        exoPlayer = null
        currentVideoUrl = null
    }

    fun getLastPosition(): Long = lastPosition

    fun wasPlayerPlaying(): Boolean = wasPlaying
}
