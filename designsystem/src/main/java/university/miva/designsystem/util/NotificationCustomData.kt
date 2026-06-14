package university.miva.designsystem.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PushNotificationData(
    val payload: PushNotificationPayload,
)

@Serializable
data class PushNotificationPayload(
    @SerialName("courseid")
    val courseId: String? = null,
    @SerialName("modulename")
    val moduleName: String? = null,
    val component: String? = null,
    val name: String? = null,
    val subject: String? = null,
    @SerialName("smallmessage")
    val smallMessage: String? = null,
    val notification: Int? = 0,
    @SerialName("contextUrlName")
    val contexturlname: String? = null,
    @SerialName("customdata")
    val customData: String? = null,
    @SerialName("userfromid")
    val userFromId: String? = null,
    @SerialName("userfromfullname")
    val userFromFullName: String? = null,
    val date: String? = null,
    @SerialName("discussionid")
    val discussionId: String? = null,
    @SerialName("postid")
    val postId: String? = null,
)
