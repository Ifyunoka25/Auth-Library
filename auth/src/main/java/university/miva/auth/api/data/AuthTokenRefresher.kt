package university.miva.auth.api.data

interface AuthTokenRefresher {
    suspend fun refreshToken(sisBaseUrl: String): AuthResult<Unit>
}
