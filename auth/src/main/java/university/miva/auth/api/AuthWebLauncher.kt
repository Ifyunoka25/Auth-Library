package university.miva.auth.api

import android.content.Context
import university.miva.auth.api.web.AuthWebRequest

interface AuthWebLauncher {
    fun open(request: AuthWebRequest, context: Context)

    fun openLogin(
        context: Context,
        url: String,
    ) = open(AuthWebRequest(url = url), context)
}
