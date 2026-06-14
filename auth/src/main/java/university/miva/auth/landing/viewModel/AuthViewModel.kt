package university.miva.auth.landing.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import university.miva.auth.api.data.AuthResult
import university.miva.auth.api.data.AuthSessionStore
import university.miva.auth.api.data.AuthTokenRefresher
import university.miva.auth.api.data.AuthTokenStore
import university.miva.designsystem.util.NetworkMonitor

class AuthViewModel(
    private val tokenRefresher: AuthTokenRefresher,
    private val sessionStore: AuthSessionStore,
    private val networkMonitor: NetworkMonitor,
    tokenStore: AuthTokenStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun getCurrentSession() = sessionStore.getSession()

    val getUserToken = tokenStore.getToken()?.accessToken

    val isOnline =
        networkMonitor.isOnline
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun refreshToken(sisBaseUrl: String) {
        viewModelScope.launch {
            val result = tokenRefresher.refreshToken(sisBaseUrl)

            _uiState.value =
                when (result) {
                    is AuthResult.Success -> {
                        AuthUiState.Success("Token refreshed")
                    }

                    is AuthResult.Error -> {
                        AuthUiState.Error(result.message)
                    }
                }
        }
    }
}

sealed class AuthUiState {
    data object Idle : AuthUiState()

    data object Loading : AuthUiState()

    data class Success(
        val data: Any,
    ) : AuthUiState()

    data class Error(
        val message: String,
    ) : AuthUiState()
}
