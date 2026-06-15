package university.miva.guest.api

interface GuestAnalytics {
    fun log(
        event: String,
        parameters: Map<String, String> = emptyMap(),
    )
}
