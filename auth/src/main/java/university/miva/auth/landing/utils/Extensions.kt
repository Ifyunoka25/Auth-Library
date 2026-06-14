package university.miva.auth.landing.utils

import university.miva.auth.api.AuthConstants

const val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?#&.])[A-Za-z\\d@\$!%*#?&.]{8,}$"
const val validStudentEmailRegex = "^[a-zA-Z0-9._%+-]+@(miva\\.edu\\.ng|ulesson\\.com)$"

@Deprecated("Use AuthConstants.APPLICANT_ENROLLED from the public auth API.")
const val APPLICANT_ENROLLED = AuthConstants.APPLICANT_ENROLLED

@Deprecated("Use AuthConstants.ENROLLMENT_STATUS from the public auth API.")
const val ENROLLMENT_STATUS = AuthConstants.ENROLLMENT_STATUS
