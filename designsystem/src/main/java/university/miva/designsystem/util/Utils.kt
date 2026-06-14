package university.miva.designsystem.util

import kotlinx.serialization.Serializable
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.roundToInt

@Serializable
data class ApplicantDashboardHostScreenArg(
    val bottomNavigationTabId: Int? = null,
)

const val NAIRA = "₦"
const val DOLLAR = "$"

@Serializable
data class StudentDashboardHostScreenArg(
    val bottomNavigationTabId: Int? = null,
    val courseTitle: String? = null,
    val courseCode: String? = null,
    val courseId: String? = null,
    val activityId: String? = null,
)

@Serializable
data class TodoArg(
    val id: String? = null,
)

data class TuitionPriceInfo(
    val discountedPriceFormatted: String,
    val originalPriceFormatted: String,
    val savedAmountFormatted: String,
)

fun formatAmount(amount: Double): String {
    val symbols =
        DecimalFormatSymbols(Locale.US).apply {
            groupingSeparator = ','
            decimalSeparator = '.'
        }

    val formatter = DecimalFormat("#,##0", symbols)
    return formatter.format(amount.roundToInt())
}
