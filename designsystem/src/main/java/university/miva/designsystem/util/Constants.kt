package university.miva.designsystem.util

import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

object Constants {
    val faqs =
        mapOf(
            "Is Miva licensed/accredited by the NUC?" to "Miva Open University is a degree-awarding open university, " +
                "recognised and licensed by the National Universities Commission. " +
                "Rest assured, our programmes adhere " +
                "to rigorous standards of academic excellence and quality.",
            "Can you get a job with an online degree?" to
                "Our curriculum empowers you for real-world success, granting you a competitive advantage in today’s job market. With online education gaining recognition, employers value degrees from esteemed institutions like ours.",
            "Am I required to take JAMB/UTME?" to
                "Please note that submission of Joint Admissions and Matriculation Board (JAMB) results is not mandatory at this stage. However, upon admission to the university, the WAEC, NECO, GCE, or NABTEB results you provided will be verified for authenticity, and JAMB Regularisation obtained.",
            "Are Miva graduates eligible to participate in the NYSC Programme?" to
                "Miva University students are excluded from participating in the  NYSC programme. Our graduates will receive a “Letter of exclusion from NYSC”, which grants them the same rights as an NYSC certificate.",
            "What are the requirements to study at Miva Open University?" to
                "To apply, you need WAEC, NECO, GCE, or NABTEB. The result must include a minimum of five credits in the course-relevant subjects (including English and Mathematics) in not more than two sittings. Application is free!",
            "What is the duration of a bachelor's degree programme at Miva Open University?" to
                "The standard duration to earn a degree at Miva Open University is four (4) years which is the same duration as earning a degree through traditional, in-person education.",
            "Are open universities better than traditional universities?" to
                "Open universities offer several advantages such as flexibility, accessibility, and convenience, allowing students to balance their education with other personal commitments. You can study from anywhere in the world and pursue your dreams at the same time.",
            "How much is Miva’s tuition fee?" to
                "Our tuition fees start at just ₦280,000 per year . We also provide flexible payment plans to ensure that financing your education is convenient and manageable.",
            "How do the online classes work?" to
                "You will learn in a virtual classroom using Miva Open University’s learning management system. You can access pre-recorded content at your convenience and engage in real-time activities with other learners. The learning management system enables interaction between you, other students and teachers, offering access to course materials, discussion forums, multimedia content, and assessments.",
            "Will I need to physically attend lectures?" to
                "Miva Open University offers online lectures. Optional meets and greets and other engagement events may be organised for students to interact with faculty members and classmates. In-person attendance is not compulsory.",
            "How long does it take to earn a degree at Miva?" to
                "The standard duration to earn a degree at Miva Open University is four (4) years which is the same duration as earning a degree through traditional, in-person education.",
            "What countries are Miva University courses available for?" to
                "Students from all over the world are encouraged to apply to Miva Open University. The students need only verify that they have acquired O’ level qualifications.",
            "Can I take study breaks?" to
                "Yes, you can take study breaks. You would, however, need to contact the academic support team to enable you to properly structure your breaks.",
            "How will I take my exams?" to
                "At Miva Open University examinations are administered at our physical centers across various states in Nigeria. For students located outside Nigeria, examinations are facilitated through proctored arrangements.",
        )
    val phoneWidth = 412.dp
    val mivaWebSite = "https://miva.university/"
    val mivaApplyWebSite = "https://miva.university/apply/"
    val mivaGetStartedLink = "https://miva.edu.ng/get-started/"
    val mivaMbaLink = "https://mba.miva.university/apply/"
    val mivaMpaLink = "https://mpa.miva.university/apply/"
    val mivaMitLink = "https://mit.miva.university/apply/"
    val mivaMphLink = "https://mph.miva.university/apply/"
    val mivaMastersApplicationLink = "https://miva.university/masters-application/"
    val privatePolicyLink = "https://miva.university/privacy-policy/"
    val termsOfService = "https://miva.university/terms-of-service/"
    const val CREATED_PIN = "created_pin"
    const val FROM_FORGOT_PIN = "from_forgot_pin"
    const val FROM_CREATE_PIN = "from_create_pin"
    const val FROM_CHANGE_PIN = "from_change_pin"
    const val FROM_CURRENT_PIN = "current_pin"
    const val CODE = "code"
    const val STATUS = "status"
    const val SUCCESS = "success"
    const val NAIRA_SYMBOL = "NG"
    const val ENGLISH_LOCALE = "en"
    const val URL = "url"
    const val SUCCESS_ = "SUCCESS"
}

const val DEFAULT_CURRENCY = "NGN"
const val DIRECT_ENTRY = "DIRECT_ENTRY"

fun formatDateString(dateString: String): String =
    try {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, dateFormatter)

        val day = date.dayOfMonth
        val month = date.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        val year = date.year

        val daySuffix =
            when {
                day in 11..13 -> "th"
                day % 10 == 1 -> "st"
                day % 10 == 2 -> "nd"
                day % 10 == 3 -> "rd"
                else -> "th"
            }
        "${day}$daySuffix $month $year"
    } catch (e: Exception) {
        try {
            val yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM")
            val yearMonth = YearMonth.parse(dateString, yearMonthFormatter)

            val month = yearMonth.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
            val year = yearMonth.year
            "$month $year"
        } catch (e2: Exception) {
            "Invalid Date Format"
        }
    }

fun getTimeFromTimestamp(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    val format = SimpleDateFormat("HH:mm a", Locale.getDefault())
    return format.format(date)
}

fun getDateFromTimestamp(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(date)
}

fun getPlainTextFromHtml(html: String): String =
    HtmlCompat
        .fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        .toString()
        .trim()
