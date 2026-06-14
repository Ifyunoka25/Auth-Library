package university.miva.designsystem.util

import androidx.navigation.NavController
import timber.log.Timber
import university.miva.designsystem.bottomNav.StudentDashboardBottomNavTab

fun NavController.navigateToDashboard(
    tab: StudentDashboardBottomNavTab? = null,
    courseTitle: String? = null,
    courseCode: String? = null,
    courseId: String? = null,
    activityId: String? = null,
) {
    Timber.tag("zzserd").d("navigateToDashboard tab: ${tab?.id}|| courseId: $courseId || activityId: $activityId")
    val params =
        buildList {
            // Always have a valid tab value
            add("bottomNavigationTabId=${tab?.id ?: 0}")
            // Append courseId only if it's non-null
            courseTitle?.let { add("courseTitle=$it") }
            courseCode?.let { add("courseCode=$it") }
            courseId?.let { add("courseId=$it") }
            activityId?.let { add("activityId=$it") }
        }.joinToString("&")

    navigate("${Routes.STUDENT_DASHBOARD_BASE}?$params") {
        launchSingleTop = true
        restoreState = true
    }
}
