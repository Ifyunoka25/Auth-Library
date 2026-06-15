package university.miva.guest.ui.utils

import university.miva.guest.ui.viewmodel.models.ProgrammeInfo

fun generateThumbnailUrls(videoId: String): List<String> =
    listOf(
        "https://img.youtube.com/vi/$videoId/maxresdefault.jpg",
        "https://img.youtube.com/vi/$videoId/hqdefault.jpg",
        "https://img.youtube.com/vi/$videoId/mqdefault.jpg",
        "https://img.youtube.com/vi/$videoId/sddefault.jpg",
    )

fun extractYouTubeId(videoUrl: String): String {
    val pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed/)[^#&?]*".toRegex()
    return pattern.find(videoUrl)?.value ?: videoUrl
}

fun ProgrammeInfo.isPostGraduates(): Boolean {
    val keywords = listOf("MBA", "MPA", "MIT", "MPH")
    return keywords.any { keyword -> name.contains(keyword, ignoreCase = true) }
}

fun ProgrammeInfo.isPostGraduatesCode(): Boolean {
    val keywords = listOf("MBA", "MPA", "MIT", "MPH")
    val code = programmeCode.orEmpty()
    return keywords.any { keyword -> code.contains(keyword, ignoreCase = true) }
}

fun getSemesterOrder(semesterName: String): Int {
    val upperCaseName = semesterName.uppercase()
    return when {
        upperCaseName.contains("FIRST") -> 1
        upperCaseName.contains("SECOND") -> 2
        upperCaseName.contains("THIRD") -> 3
        upperCaseName.contains("FOURTH") -> 4
        upperCaseName.contains("FIFTH") -> 5
        upperCaseName.contains("SIXTH") -> 6
        else -> 99 // Any unknown semesters go to the end
    }
}

const val EMAIL = "EMAIL"
const val EMAIL_URL = "mailto:admissions@miva.university"
