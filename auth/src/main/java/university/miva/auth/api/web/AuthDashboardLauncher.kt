package university.miva.auth.api.web

import android.content.Context

interface AuthDashboardLauncher {
    fun launchDashboard(
        context: Context,
        ticket: String,
        enrollmentStatus: String,
    )
}
