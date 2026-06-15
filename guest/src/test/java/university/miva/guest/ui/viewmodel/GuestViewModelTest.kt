package university.miva.guest.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import university.miva.guest.api.GuestDeviceInfoProvider
import university.miva.guest.api.data.GuestCurriculumCourse
import university.miva.guest.api.data.GuestCurriculumData
import university.miva.guest.api.data.GuestCurriculumLevel
import university.miva.guest.api.data.GuestCurriculumSemester
import university.miva.guest.api.data.GuestDataResult
import university.miva.guest.api.data.GuestFacultyData
import university.miva.guest.api.data.GuestFacultyResponse
import university.miva.guest.api.data.GuestProgramRepository
import university.miva.guest.api.data.GuestProgramme
import university.miva.guest.api.data.GuestProgrammeData
import university.miva.guest.api.data.GuestTestimonial

@OptIn(ExperimentalCoroutinesApi::class)
class GuestViewModelTest {
    private val guestProgramRepository: GuestProgramRepository = mockk()
    private val deviceInfoProvider = TestGuestDeviceInfoProvider()
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: GuestViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle()
        viewModel = GuestViewModel(savedStateHandle, guestProgramRepository, deviceInfoProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadFaculties emits Loading then Success on successful API call`() {
        coEvery { guestProgramRepository.getGuestFaculties() } returns GuestDataResult.Success(faculties)
        coEvery { guestProgramRepository.getGuestProgrammes("1234") } returns GuestDataResult.Success(programmes)
        coEvery { guestProgramRepository.getGuestCurriculum(any(), any()) } returns GuestDataResult.Success(curriculumLevels)
        coEvery { guestProgramRepository.getTestimonials() } returns GuestDataResult.Success(emptyList())

        runTest {
            val collectedStates = mutableListOf<GuestUiState>()

            val job =
                launch(UnconfinedTestDispatcher(testScheduler)) {
                    viewModel = GuestViewModel(savedStateHandle, guestProgramRepository, deviceInfoProvider)
                    viewModel.loadFaculties()
                    viewModel.uiState.toList(collectedStates)
                }
            advanceUntilIdle()

            assertEquals(2, collectedStates.size)
            assertTrue(collectedStates[0] is GuestUiState.Loading)

            val successState = collectedStates[1]
            assertTrue(successState is GuestUiState.ProgrammesSuccess)
            advanceUntilIdle()

            val facultiesResult = (successState as GuestUiState.ProgrammesSuccess).faculties
            assertEquals(1, facultiesResult.size)
            job.cancel()
        }
    }

    @Test
    fun `initialization successfully loads and maps testimonials into testimonials flow`() =
        runTest {
            val testimonialData =
                listOf(
                    GuestTestimonial(
                        id = "id1",
                        title = "Test 1",
                        thumbnail = "thumb1",
                        link = "video1",
                    ),
                    GuestTestimonial(
                        id = "id2",
                        title = "Test 2",
                        thumbnail = "thumb2",
                        link = "video2",
                    ),
                )
            coEvery { guestProgramRepository.getTestimonials() } returns GuestDataResult.Success(testimonialData)
            coEvery { guestProgramRepository.getGuestFaculties() } returns
                GuestDataResult.Success(GuestFacultyResponse(faculties = emptyList(), total = 0))

            viewModel = GuestViewModel(savedStateHandle, guestProgramRepository, deviceInfoProvider)
            advanceUntilIdle()

            val resultTestimonials = viewModel.testimonials.value
            assertEquals(2, resultTestimonials.size)
            assertEquals("Test 1", resultTestimonials[0].name)
        }

    private val faculties =
        GuestFacultyResponse(
            faculties =
                listOf(
                    GuestFacultyData(
                        facultyId = "1234",
                        name = "Test",
                        description = "Test",
                        image = "Test",
                        shortName = "Test",
                        createdAt = "time",
                        updatedAt = "time",
                        averageTuitionInDollar = 100.0,
                        averageTuitionInNaira = 100.0,
                        totalProgrammes = 1,
                    ),
                ),
            total = 1,
        )

    private val curriculumLevels =
        GuestCurriculumData(
            levels =
                listOf(
                    GuestCurriculumLevel(
                        level = "Test",
                        semesters =
                            listOf(
                                GuestCurriculumSemester(
                                    semester = "Test",
                                    courses =
                                        listOf(
                                            GuestCurriculumCourse(
                                                courseId = "1234",
                                                courseName = "Test",
                                                courseCode = "Test",
                                                creditUnit = 1,
                                                courseType = "Test",
                                            ),
                                        ),
                                ),
                            ),
                    ),
                ),
        )

    private val programmes =
        GuestProgrammeData(
            programmes =
                listOf(
                    GuestProgramme(
                        programmeId = "1234",
                        name = "Test",
                        image = "Test",
                        description = "Test",
                        type = "Test",
                        programmeCode = "Test",
                        shortName = "Test",
                        facultyId = "Test",
                        facultyName = "Test",
                        status = "Test",
                        duration = 1,
                        programmeUnit = 1,
                        tuitionFeePerSemesterInDollar = 100.0,
                        tuitionFeePerSemesterInNaira = 100.0,
                        yearlyTuitionInDollar = 100.0,
                        yearlyTuitionInNaira = 100.0,
                        createdAt = "time",
                        updatedAt = "time",
                        numberOfSemesters = 1,
                        discountApplied = 10.0,
                    ),
                ),
            total = 1,
        )
}

private class TestGuestDeviceInfoProvider : GuestDeviceInfoProvider {
    override val isOnline: Flow<Boolean> = MutableStateFlow(true)

    override suspend fun getCountryCode(): String = "NG"
}
