package university.miva.auth.api

interface AuthAnalytics {
    fun trackPersonaSelected(persona: AuthPersona)

    fun trackLoginSucceeded() = Unit

    fun trackLogoutCompleted() = Unit
}

enum class AuthPersona {
    PROSPECTIVE_STUDENT,
    APPLICANT,
    CURRENT_STUDENT,
}

object NoOpAuthAnalytics : AuthAnalytics {
    override fun trackPersonaSelected(persona: AuthPersona) = Unit
}
