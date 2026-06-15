package university.miva.guest.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import university.miva.guest.api.DEFAULT_GUEST_COUNTRY_CODE
import university.miva.guest.api.GuestDeviceInfoProvider
import university.miva.guest.api.data.GuestCurriculumLevel
import university.miva.guest.api.data.GuestDataResult
import university.miva.guest.api.data.GuestFacultyData
import university.miva.guest.api.data.GuestProgramRepository
import university.miva.guest.api.data.GuestProgramme
import university.miva.guest.api.data.GuestTuitionData
import university.miva.guest.ui.viewmodel.models.FacultyInfo
import university.miva.guest.ui.viewmodel.models.ProgrammeInfo
import university.miva.guest.ui.viewmodel.models.curriculumDescriptionMap
import university.miva.guest.ui.viewmodel.models.facultyLogoMap
import university.miva.guest.ui.viewmodel.models.facultyPageImageMap
import university.miva.guest.ui.viewmodel.models.modeOfStudyMap
import university.miva.guest.ui.viewmodel.models.programmeRequirementsInfoMap
import java.text.Normalizer

class GuestViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val guestProgramRepository: GuestProgramRepository,
    private val deviceInfoProvider: GuestDeviceInfoProvider,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<GuestUiState>(GuestUiState.Loading)
    val uiState = _uiState.asStateFlow()
    var selectedVideoUrl by mutableStateOf<String?>(null)
    var selectedVideoThumbnail by mutableStateOf<String?>(null)
    private val _testimonials = MutableStateFlow<List<Testimonial>>(emptyList())
    val testimonials = _testimonials.asStateFlow()
    val isOnline =
        deviceInfoProvider.isOnline
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // Playback state saved across config changes and process death
    var playbackPosition by mutableFloatStateOf(
        savedStateHandle.get<Float>(KEY_PLAYBACK_POSITION) ?: 0f,
    )
        private set

    // For tracking if we should resume playback
    var wasPlaying by mutableStateOf(
        savedStateHandle.get<Boolean>(KEY_WAS_PLAYING) ?: false,
    )
        private set

    fun updatePlaybackState(
        position: Float,
        playing: Boolean,
    ) {
        playbackPosition = position
        wasPlaying = playing
        savedStateHandle[KEY_PLAYBACK_POSITION] = position
        savedStateHandle[KEY_WAS_PLAYING] = playing
    }

    fun clearPlaybackState() {
        savedStateHandle.remove<Float>(KEY_PLAYBACK_POSITION)
        savedStateHandle.remove<Boolean>(KEY_WAS_PLAYING)
        playbackPosition = 0f
        wasPlaying = false
    }

    init {
        viewModelScope.launch {
            launch { loadFaculties() }
            launch { loadTestimonials() }
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            launch { loadFaculties() }
            launch { loadTestimonials() }
        }
    }

    fun loadFaculties() {
        viewModelScope.launch {
            _uiState.update { GuestUiState.Loading }

            when (val result = guestProgramRepository.getGuestFaculties()) {
                is GuestDataResult.Success -> {
                    // The keywords to filter out.
                    val filterKeywords = listOf("testing", "qa")
                    val faculties =
                        result.data.faculties.filterNot { faculty ->
                            filterKeywords.any { keyword ->
                                faculty.name?.contains(keyword, ignoreCase = true) ?: false
                            }
                        }

                    val facultyInfos =
                        result.data.faculties
                            .map { it.toFacultyInfo() }

                    val countryCode = deviceInfoProvider.getCountryCode() ?: DEFAULT_GUEST_COUNTRY_CODE
                    val programmes =
                        coroutineScope {
                            faculties
                                .map { faculty ->
                                    async { getProgrammesForFaculty(faculty.facultyId) }
                                }.awaitAll()
                                .flatten()
                                .filterNot { program ->
                                    filterKeywords.any { keyword ->
                                        program.name.contains(keyword, ignoreCase = true)
                                    }
                                }
                        }

                    val mappedProgrammes = programmes.map { it.toProgrammeInfo() }

                    val curriculums =
                        coroutineScope {
                            programmes
                                .map { programme ->
                                    async { getGuestCurriculum(programme.facultyId, programme.programmeId) }
                                }.awaitAll()
                                .flatten()
                        }

                    Timber.tag("GuestViewModel").d("Programmes: $programmes")
                    _uiState.update {
                        GuestUiState.ProgrammesSuccess(
                            faculties = facultyInfos,
                            programmes = mappedProgrammes,
                            curriculums = curriculums,
                            countryCode = countryCode,
                        )
                    }
                }

                is GuestDataResult.Error -> {
                    _uiState.update {
                        GuestUiState.Error(errorMessage = result.message)
                    }
                }
            }
        }
    }

    private suspend fun getProgrammesForFaculty(facultyId: String): List<GuestProgramme> =
        when (val result = guestProgramRepository.getGuestProgrammes(facultyId)) {
            is GuestDataResult.Success -> {
                Timber.tag("GuestViewModel").d("Programmes: ${result.data.programmes}")
                result.data.programmes
            }

            is GuestDataResult.Error -> {
                Timber.tag("GuestViewModel").e("Error fetching programmes for faculty $facultyId: ${result.message}")
                emptyList()
            }
        }

    private suspend fun getGuestCurriculum(
        facultyId: String,
        programmeId: String,
    ): List<GuestCurriculumLevel> =
        when (val result = guestProgramRepository.getGuestCurriculum(facultyId, programmeId)) {
            is GuestDataResult.Success -> {
                Timber.tag("GuestViewModel").d("Curriculum: ${result.data.levels}")
                result.data.levels
            }
            is GuestDataResult.Error -> {
                Timber.tag("GuestViewModel").e("Error getting curriculum: ${result.message}")
                emptyList()
            }
        }

    private suspend fun getGuestProgrammeDetails(
        facultyId: String,
        programmeId: String,
    ): GuestProgramme? =
        when (val result = guestProgramRepository.getGuestProgrammeDetails(facultyId, programmeId)) {
            is GuestDataResult.Success -> result.data.programme
            is GuestDataResult.Error -> {
                Timber.tag("GuestViewModel").e("Error getting programme details: ${result.message}")
                null
            }
        }

    private suspend fun getGuestTuition(
        facultyId: String,
        programmeId: String,
    ): GuestTuitionData? =
        when (val result = guestProgramRepository.getGuestTuition(facultyId, programmeId)) {
            is GuestDataResult.Success -> result.data.tuition
            is GuestDataResult.Error -> {
                Timber.tag("GuestViewModel").e("Error getting tuition: ${result.message}")
                null
            }
        }

    fun loadTestimonials() {
        viewModelScope.launch {
            _uiState.value = GuestUiState.Loading
            when (val result = guestProgramRepository.getTestimonials()) {
                is GuestDataResult.Success -> {
                    _testimonials.value =
                        result.data.map {
                            Testimonial(
                                id = it.id,
                                name = it.title,
                                thumbnail = it.thumbnail,
                                videoUrl = it.link,
                            )
                        }
                }

                is GuestDataResult.Error -> {
                    _uiState.value = GuestUiState.Error(result.message)
                }
            }
        }
    }

    companion object {
        private const val KEY_PLAYBACK_POSITION = "playbackPosition"
        private const val KEY_WAS_PLAYING = "wasPlaying"
    }
}

sealed class GuestUiState {
    data object Loading : GuestUiState()

    data class ProgrammesSuccess(
        val faculties: List<FacultyInfo> = emptyList(),
        val programmes: List<ProgrammeInfo> = emptyList(),
        val curriculums: List<GuestCurriculumLevel> = emptyList(),
        val countryCode: String = "",
    ) : GuestUiState()

    data class Success(
        val notice: Notice? = null,
    ) : GuestUiState()

    data class Error(
        val errorMessage: String,
    ) : GuestUiState()
}

fun GuestFacultyData.toFacultyInfo(): FacultyInfo {
    val pageImage = facultyPageImageMap[name?.trim()]
    val logo = facultyLogoMap[name?.trim()]
    return FacultyInfo(
        facultyId = facultyId,
        name = name,
        description = description,
        image = image,
        pageImage = pageImage,
        logo = logo,
        shortName = shortName,
        createdAt = createdAt,
        updatedAt = updatedAt,
        averageTuitionInNaira = averageTuitionInNaira,
        averageTuitionInDollar = averageTuitionInDollar,
        totalProgrammes = totalProgrammes,
    )
}

fun GuestProgramme.toProgrammeInfo(): ProgrammeInfo {
    val programmeRequirements = programmeRequirementsInfoMap[name.trim()]
    val programmeModeOfStudy = modeOfStudyMap[name.trim()]
    val curriculumDescription = curriculumDescriptionMap[name.trim()]

    return ProgrammeInfo(
        programmeId = programmeId,
        name = name,
        image = image,
        description = description,
        type = type,
        programmeCode = programmeCode,
        shortName = shortName,
        facultyId = facultyId,
        facultyName = facultyName,
        status = status,
        duration = duration,
        programmeUnit = programmeUnit,
        tuitionFeePerSemesterInNaira = tuitionFeePerSemesterInNaira,
        tuitionFeePerSemesterInDollar = tuitionFeePerSemesterInDollar,
        yearlyTuitionInNaira = yearlyTuitionInNaira,
        yearlyTuitionInDollar = yearlyTuitionInDollar,
        createdAt = createdAt,
        updatedAt = updatedAt,
        numberOfSemesters = numberOfSemesters,
        discountApplied = discountApplied,
        modeOfStudy = programmeModeOfStudy,
        curriculumDescription = curriculumDescription,
        programmeRequirementsInfo = programmeRequirements,
    )
}

data class Testimonial(
    val id: String,
    val name: String,
    val videoUrl: String,
    val thumbnail: String,
)

fun String.normalize(): String =
    Normalizer
        .normalize(this, Normalizer.Form.NFD)
        .replace("\\p{Mn}+".toRegex(), "") // strip diacritics
        .lowercase()

private fun String?.normContains(q: String): Boolean = this?.normalize()?.contains(q) == true

fun ProgrammeInfo.matchesQuery(q: String): Boolean {
    if (q.isBlank()) return true
    // Search across the common fields you expose in UI
    return name.normContains(q) ||
        programmeCode.normContains(q) ||
        facultyName.normContains(q) ||
        description.normContains(q) ||
        shortName.normContains(q)
}
