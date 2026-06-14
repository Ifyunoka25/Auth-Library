package university.miva.auth.api.data

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
)
