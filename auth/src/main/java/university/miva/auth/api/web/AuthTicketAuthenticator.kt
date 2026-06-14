package university.miva.auth.api.web

import university.miva.auth.api.data.AuthResult

interface AuthTicketAuthenticator {
    suspend fun authenticate(
        ticket: String,
        casBaseUrl: String,
    ): AuthResult<Unit>
}
