package university.miva.designsystem.util

object Routes {
    // Auth routes
    const val SPLASH = "splash"
    const val SELECT_PERSONA = "select_persona"
    const val COURSE_ENROLLMENT = "courseEnrollment"

    // MivaSave
    const val CURRENT_PIN = "current_pin"
    const val MIVA_SAVE_SETTINGS = "miva_save_settings"
    const val MIVA_SAVE_CHANGE_PIN_SUCCESS = "miva_save_change_pin_success"
    const val REQUEST_WITHDRAWAL = "request_withdrawal"
    const val REQUEST_WITHDRAWAL_BANK_DETAILS = "request_withdrawal_bank_details"
    const val CREATE_SCHEDULE = "create_schedule"
    const val CREATE_SCHEDULE_DATE = "create_schedule_date"
    const val CREATE_SCHEDULE_INTERVAL = "create_schedule_interval"
    const val MIVA_SAVE_GET_STARTED = "miva_save_get_started"
    const val MIVA_SAVE_VERIFY_IDENTITY = "ms_verify_identity"
    const val MIVA_SAVE_SETUP_WITHDRAWAL_ACCOUNT = "ms_setup_withdrawal_account"
    const val MIVA_SAVE_CREATE_PIN_SCREEN = "miva_save_create_pin_screen"
    const val MIVA_SAVE_CONFIRM_YOUR_PIN_SCREEN = "miva_save_confirm_your_pin_screen"
    const val MIVA_SAVE_DASHBOARD = "miva_save_dashboard"
    const val RECENT_TRANSACTIONS = "recent_transactions"
    const val VIEW_TRANSACTION = "view_transaction"
    const val ADD_FUNDS = "add_funds"
    const val EDIT_SCHEDULE = "edit_schedule"

    const val RESULTS = "results"
    const val PAYMENTS = "payments"
    const val VIEW_RECEIPT = "view_receipt"

    // Guest routes
    const val GUEST = "guest"
    const val POSTGRADUATE = "postgraduate"
    const val UNDERGRADUATE = "undergraduate"

    // Other routes
    const val YOUTUBE_PLAYER = "youtubePlayer/{videoUrl}"
    const val UPLOAD_DOCUMENT = "uploadDocument"

    const val STUDENT_DASHBOARD_BASE = "student_dashboard"
    const val STUDENT_DASHBOARD =
        "$STUDENT_DASHBOARD_BASE?bottomNavigationTabId={bottomNavigationTabId}&courseTitle={courseTitle}&courseCode={courseCode}&courseId={courseId}&activityId={activityId}"
    const val APPLICANT_DASHBOARD = "applicant_dashboard"
    const val APPLICANT_PERSONAL_DETAILS = "applicant_personal_details/{applicationType}"
    const val APPLICANT_PERSONAL_DETAILS_PATH = "applicant_personal_details"
    const val APPLICANT_PROGRAMME_INFO = "applicant_programme_info/{applicationType}"
    const val APPLICANT_PROGRAMME_INFO_PATH = "applicant_programme_info"
    const val APPLICANT_CONTACT_INFO = "applicant_contact_info/{applicationType}/{admissionType}"
    const val APPLICANT_CONTACT_INFO_PATH = "applicant_contact_info"
    const val APPLICANT_EMPLOYMENT_INFO = "applicant_employment_info/{applicationType}/{admissionType}"
    const val APPLICANT_EMPLOYMENT_INFO_PATH = "applicant_employment_info"
    const val APPLICANT_UPLOAD_DOCUMENT_INFO = "applicant_upload_document/{applicationType}/{admissionType}"
    const val APPLICANT_UPLOAD_DOCUMENT_INFO_PATH = "applicant_upload_document"
    const val APPLICANT_SUCCESS_SCREEN = "applicant_success_screen"

    fun applicantContactInfo(
        applicationType: String,
        admissionType: String,
    ): String = "$APPLICANT_CONTACT_INFO_PATH/$applicationType/$admissionType"

    fun applicantEmploymentInfo(
        applicationType: String,
        admissionType: String,
    ): String = "$APPLICANT_EMPLOYMENT_INFO_PATH/$applicationType/$admissionType"

    fun applicantUploadDocument(
        applicationType: String,
        admissionType: String,
    ): String = "$APPLICANT_UPLOAD_DOCUMENT_INFO_PATH/$applicationType/$admissionType"

    const val CALENDAR = "calendar"
    const val TO_DO = "todoScreen"
    const val DOWNLOADS = "downloads"
    const val OFFLINE_VID_DOWNLOADS = "offlineVideo"
    const val COURSE_DETAILS = "courseDetailsScreen"
    const val COURSE_MODULE_ANNOUNCEMENT = "courseModuleAnnouncement"
    const val COURSE_MODULE_QUIZ = "courseModuleQuiz"
    const val COURSE_ANNOUNCEMENT_DETAILS = "courseAnnouncementDetailsScreen"
    const val COURSE_ACTIVITY_DETAILS = "courseActivityDetailsScreen"
    const val DISCUSSION_FORUM = "discussionForum"
    const val DISCUSSION_DETAILS = "discussion_detail"
    const val DISCUSSION_REPLY_THREAD = "discussion_reply_thread"
    const val COURSE_MODULE_ASSIGNMENT = "courseModuleAssignment"
    const val COURSE_MODULE_ASSIGNMENT_SUBMISSION = "courseModuleAssignmentSubmission"
    const val COURSE_MODULE_PEER_ASSESSMENT = "courseModulePeerAssessment"
    const val COURSE_MODULE_PEER_ASSESSMENT_DETAILS = "courseModulePeerAssessmentDetails"
    const val PEER_ASSESSMENT_VIEW_SUBMISSION = "PeerAssessmentViewSubmission"
    const val CHOICE = "choice"
    const val INBOX_DETAILS = "inboxDetails"
    const val PEER_RATING = "peer_rating"
    const val VIEW_TASK = "view_task"
    const val NOTIFICATION = "notification"

    const val PDF_VIEWER = "pdf_viewer"
    const val VIEW_PROFILE_IMAGE = "view_profile_image"

    const val SETTINGS = "settings"
    const val DOWNLOAD_QUALITY = "download_quality"

    // Helper functions for parameterized routes

    fun youtubePlayer(videoUrl: String) = "youtubePlayer/$videoUrl"

    const val FACULTY = "faculty"
    const val FACULTY_DETAILS = "faculty_details"
    const val FULLSCREEN_VIDEO_PLAYER = "fullscreen_video_player"
    const val PROFILE_SCREEN = "profile_screen"
}

object BundleKeys {
    object FacultyDetails {
        const val programme = "programme"
    }
}
