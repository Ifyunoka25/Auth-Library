package university.miva.guest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import university.miva.guest.api.DEFAULT_GUEST_COUNTRY_CODE
import university.miva.guest.api.GuestDeviceInfoProvider
import university.miva.guest.api.data.GuestCurriculumCourse
import university.miva.guest.api.data.GuestCurriculumLevel
import university.miva.guest.api.data.GuestCurriculumSemester
import university.miva.guest.api.data.GuestDataResult
import university.miva.guest.api.data.GuestProgramRepository
import university.miva.guest.ui.viewmodel.models.CurriculumCourseInfo
import university.miva.guest.ui.viewmodel.models.CurriculumLevelInfo
import university.miva.guest.ui.viewmodel.models.CurriculumSemesterInfo
import university.miva.guest.ui.viewmodel.models.ProgrammeInfo

class GuestDetailsViewModel(
    private val guestProgramRepository: GuestProgramRepository,
    private val deviceInfoProvider: GuestDeviceInfoProvider,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<GuestDetailsUiState>(GuestDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _notice = MutableStateFlow<Notice?>(null)
    val notice = _notice.asStateFlow()

    val isOnline =
        deviceInfoProvider.isOnline
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _code = MutableStateFlow("")
    val code = _code.asStateFlow()

    init {
        viewModelScope.launch {
            val countryCode = deviceInfoProvider.getCountryCode() ?: DEFAULT_GUEST_COUNTRY_CODE
            _code.value = countryCode
        }
    }

    fun getGuestCurriculum(programme: ProgrammeInfo) {
        viewModelScope.launch {
            _uiState.update { GuestDetailsUiState.Loading }

            when (val result = guestProgramRepository.getGuestCurriculum(programme.facultyId, programme.programmeId)) {
                is GuestDataResult.Success -> {
                    val curriculums =
                        result.data.levels.map {
                            it.toCurriculumLevelInfo()
                        }

                    _uiState.update {
                        GuestDetailsUiState.CurriculumSuccess(
                            curriculums = curriculums,
                        )
                    }
                }

                is GuestDataResult.Error -> {
                    _uiState.update {
                        GuestDetailsUiState.Error(errorMessage = result.message)
                    }
                }
            }
        }
    }

    fun loadNotice() {
        viewModelScope.launch {
            when (val response = guestProgramRepository.getNotice()) {
                is GuestDataResult.Success -> {
                    val notice =
                        Notice(
                            id = response.data.id,
                            title = response.data.title,
                            body = response.data.body,
                        )
                    _notice.value = notice
                }
                is GuestDataResult.Error -> {
                    _uiState.update {
                        GuestDetailsUiState.Error(errorMessage = response.message)
                    }
                }
            }
        }
    }
}

fun GuestCurriculumLevel.toCurriculumLevelInfo(): CurriculumLevelInfo =
    CurriculumLevelInfo(
        level = this.level,
        semesters = this.semesters.map { it.toCurriculumSemesterInfo() },
    )

fun GuestCurriculumSemester.toCurriculumSemesterInfo(): CurriculumSemesterInfo =
    CurriculumSemesterInfo(
        semester = this.semester,
        courses = this.courses.map { it.toCurriculumCourseInfo() },
    )

fun GuestCurriculumCourse.toCurriculumCourseInfo(): CurriculumCourseInfo =
    CurriculumCourseInfo(
        courseId = this.courseId,
        courseName = this.courseName,
        courseCode = this.courseCode,
        creditUnit = this.creditUnit,
        courseType = this.courseType,
    )

sealed class GuestDetailsUiState {
    data object Loading : GuestDetailsUiState()

    data class CurriculumSuccess(
        val curriculums: List<CurriculumLevelInfo> = emptyList(),
    ) : GuestDetailsUiState()

    data class Error(
        val errorMessage: String,
    ) : GuestDetailsUiState()
}

data class Notice(
    val id: String,
    val title: String,
    val body: String,
)
