package university.miva.auth.api.data

interface AuthSessionStore {
    fun getSession(): AuthUserSession?

    fun clear()
}
