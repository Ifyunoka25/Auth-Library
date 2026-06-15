package university.miva.designsystem.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class NetworkMonitor(
    context: Context,
) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isOnline: Flow<Boolean> =
        callbackFlow {
            val callback =
                object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        trySend(true)
                    }

                    override fun onLost(network: Network) {
                        trySend(false)
                    }
                }

            val request = NetworkRequest.Builder().build()
            connectivityManager.registerNetworkCallback(request, callback)

            // Emit current status
            trySend(connectivityManager.isCurrentlyOnline())

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
}

private fun ConnectivityManager.isCurrentlyOnline(): Boolean {
    val activeNetwork = activeNetwork ?: return false
    val capabilities = getNetworkCapabilities(activeNetwork) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}

@Serializable
private data class ApiErrorPayload(
    val status: String? = null,
    val message: String? = null,
    val errors: List<String>? = null,
)

private val errorJson =
    Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

fun extractDisplayError(raw: String): String =
    try {
        val e = errorJson.decodeFromString<ApiErrorPayload>(raw)

        buildString {
            // "Status: message"
            e.status?.takeIf { it.isNotBlank() }?.let { append(it) }
            e.message?.takeIf { it.isNotBlank() }?.let {
                if (isNotEmpty()) append(": ")
                append(it)
            }

            // " — err1 · err2 · +N more…"
            val errs = e.errors.orEmpty().filter { it.isNotBlank() }
            if (errs.isNotEmpty()) {
                val shown = errs.take(3)
                val more = errs.size - shown.size
                if (isNotEmpty()) append(" — ")
                append(shown.joinToString(" · "))
                if (more > 0) append(" · +").append(more).append(" more…")
            }
        }.ifBlank { raw }
    } catch (_: Exception) {
        // Fallback regex if server returns non-strict JSON
        val status = "\"status\"\\s*:\\s*\"([^\"]+)\"".toRegex().find(raw)?.groupValues?.getOrNull(1)
        val message = "\"message\"\\s*:\\s*\"([^\"]+)\"".toRegex().find(raw)?.groupValues?.getOrNull(1)

        // Try to grab errors array items: "errors": ["a","b",...]
        val errorsBlock = "\"errors\"\\s*:\\s*\\[(.*?)]".toRegex().find(raw)?.groupValues?.getOrNull(1)
        val errs =
            errorsBlock
                ?.let { "\"([^\"]+)\"".toRegex().findAll(it).map { m -> m.groupValues[1] }.toList() }
                .orEmpty()

        buildString {
            if (!status.isNullOrBlank()) append(status)
            if (!message.isNullOrBlank()) {
                if (isNotEmpty()) append(": ")
                append(message)
            }
            if (errs.isNotEmpty()) {
                val shown = errs.take(3)
                val more = errs.size - shown.size
                if (isNotEmpty()) append(" — ")
                append(shown.joinToString(" · "))
                if (more > 0) append(" · +").append(more).append(" more…")
            }
        }.ifBlank { raw }
    }
