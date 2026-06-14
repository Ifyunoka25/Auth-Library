package university.miva.auth.api

import androidx.navigation.NavController

interface AuthNavigationHandler {
    fun openGuestExperience(navController: NavController)

    fun openApplicantDashboard(navController: NavController)

    fun openStudentDashboard(navController: NavController)
}
