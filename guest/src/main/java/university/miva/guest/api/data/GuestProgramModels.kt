package university.miva.guest.api.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuestFacultyResponse(
    val faculties: List<GuestFacultyData> = emptyList(),
    val total: Int,
)

@Serializable
data class GuestFacultyData(
    @SerialName("faculty_id")
    val facultyId: String,
    val name: String? = "",
    val description: String? = "",
    val image: String? = "",
    @SerialName("short_name")
    val shortName: String? = "",
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("average_tuition_in_naira")
    val averageTuitionInNaira: Double? = 0.0,
    @SerialName("average_tuition_in_dollar")
    val averageTuitionInDollar: Double? = 0.0,
    @SerialName("total_programmes")
    val totalProgrammes: Int? = 0,
)

@Serializable
data class GuestProgrammeData(
    val programmes: List<GuestProgramme> = emptyList(),
    val total: Int? = 0,
)

@Serializable
data class GuestProgrammeResponse(
    val programme: GuestProgramme,
)

@Serializable
data class GuestProgramme(
    @SerialName("programme_id")
    val programmeId: String,
    val name: String,
    val image: String? = "",
    val description: String? = "",
    val type: String? = "",
    @SerialName("programme_code")
    val programmeCode: String? = "",
    @SerialName("short_name")
    val shortName: String? = "",
    @SerialName("faculty_id")
    val facultyId: String,
    @SerialName("faculty_name")
    val facultyName: String? = "",
    val status: String? = "",
    val duration: Int? = 0,
    @SerialName("programme_unit")
    val programmeUnit: Int? = 0,
    @SerialName("tuition_fee_per_semester_in_naira")
    val tuitionFeePerSemesterInNaira: Double? = 0.0,
    @SerialName("tuition_fee_per_semester_in_dollar")
    val tuitionFeePerSemesterInDollar: Double? = 0.0,
    @SerialName("yearly_tuition_in_naira")
    val yearlyTuitionInNaira: Double? = 0.0,
    @SerialName("yearly_tuition_in_dollar")
    val yearlyTuitionInDollar: Double? = 0.0,
    @SerialName("created_at")
    val createdAt: String? = "",
    @SerialName("updated_at")
    val updatedAt: String? = "",
    @SerialName("number_of_semesters")
    val numberOfSemesters: Int? = 0,
    @SerialName("discount_applied")
    val discountApplied: Double? = 0.0,
)

@Serializable
data class GuestProgrammeTuitionData(
    val tuition: GuestTuitionData,
)

@Serializable
data class GuestTuitionData(
    @SerialName("tuition_per_semester_in_naira")
    val tuitionPerSemesterInNaira: Double? = 0.0,
    @SerialName("tuition_per_semester_in_dollar")
    val tuitionPerSemesterInDollar: Double? = 0.0,
    @SerialName("yearly_tuition_in_naira")
    val yearlyTuitionInNaira: Double? = 0.0,
    @SerialName("yearly_tuition_in_dollar")
    val yearlyTuitionInDollar: Double? = 0.0,
    @SerialName("discount_applied")
    val discountApplied: Double? = 0.0,
)

@Serializable
data class GuestCurriculumData(
    val levels: List<GuestCurriculumLevel> = emptyList(),
)

@Serializable
data class GuestCurriculumLevel(
    val level: String,
    val semesters: List<GuestCurriculumSemester> = emptyList(),
)

@Serializable
data class GuestCurriculumSemester(
    val semester: String,
    val courses: List<GuestCurriculumCourse> = emptyList(),
)

@Serializable
data class GuestCurriculumCourse(
    @SerialName("course_id")
    val courseId: String,
    @SerialName("course_name")
    val courseName: String? = "",
    @SerialName("course_code")
    val courseCode: String? = "",
    @SerialName("credit_unit")
    val creditUnit: Int? = 0,
    @SerialName("course_type")
    val courseType: String? = "",
)

@Serializable
data class GuestTestimonial(
    val id: String,
    val title: String,
    val thumbnail: String,
    val link: String,
)

@Serializable
data class GuestNotice(
    val id: String,
    val title: String,
    val body: String,
)
