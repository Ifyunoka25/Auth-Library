package university.miva.guest.api.data

interface GuestProgramRepository {
    suspend fun getTestimonials(): GuestDataResult<List<GuestTestimonial>>

    suspend fun getNotice(): GuestDataResult<GuestNotice>

    suspend fun getGuestFaculties(): GuestDataResult<GuestFacultyResponse>

    suspend fun getGuestProgrammes(facultyId: String): GuestDataResult<GuestProgrammeData>

    suspend fun getGuestCurriculum(
        facultyId: String,
        programmeId: String,
    ): GuestDataResult<GuestCurriculumData>

    suspend fun getGuestProgrammeDetails(
        facultyId: String,
        programmeId: String,
    ): GuestDataResult<GuestProgrammeResponse>

    suspend fun getGuestTuition(
        facultyId: String,
        programmeId: String,
    ): GuestDataResult<GuestProgrammeTuitionData>
}
