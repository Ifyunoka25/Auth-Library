package university.miva.designsystem.analytics

interface DesignSystemAnalytics {
    fun track(
        event: String,
        params: Map<String, Any?> = emptyMap(),
    )
}

object NoOpDesignSystemAnalytics : DesignSystemAnalytics {
    override fun track(
        event: String,
        params: Map<String, Any?>,
    ) = Unit
}

object DesignSystemAnalyticsProvider {
    @Volatile
    var analytics: DesignSystemAnalytics = NoOpDesignSystemAnalytics

    fun track(
        event: String,
        params: Map<String, Any?> = emptyMap(),
    ) {
        analytics.track(event, params)
    }
}

object DesignSystemVideoEvents {
    const val LESSON_STARTED = "video_lesson_started"
    const val LESSON_PROGRESSED = "video_lesson_progressed"
    const val LESSON_COMPLETED = "video_lesson_completed"
}

object DesignSystemAnalyticsParams {
    const val VIDEO = "Video"
    const val PLAYBACK_STARTED = "Playback Started"
    const val PROGRESS_STATUS = "Progress status"
}
