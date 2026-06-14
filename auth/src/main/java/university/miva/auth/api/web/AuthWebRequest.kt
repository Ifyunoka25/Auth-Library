package university.miva.auth.api.web

data class AuthWebRequest(
    val url: String,
    val isLogoutFlow: Boolean = false,
)
