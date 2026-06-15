package university.miva.guest.api

import android.content.Context

interface GuestWebLauncher {
    fun openAuthWebView(
        context: Context,
        url: String,
    )

    fun openWebView(
        context: Context,
        url: String,
    )
}
