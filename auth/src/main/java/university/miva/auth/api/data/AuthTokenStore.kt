package university.miva.auth.api.data

interface AuthTokenStore {
    fun getToken(): AuthToken?

    fun setToken(token: AuthToken)

    fun clear()
}
