package university.miva.auth.web

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import university.miva.auth.api.data.AuthResult
import university.miva.auth.api.data.AuthSessionStore
import university.miva.auth.api.web.AuthTicketAuthenticator

class WebViewModel(
    private val ticketAuthenticator: AuthTicketAuthenticator,
    private val sessionStore: AuthSessionStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun getEnrollmentStatus(): String = sessionStore.getSession()?.enrollmentStatus.orEmpty()

    fun getJwtToken(
        ticket: String,
        casBaseUrl: String,
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            _uiState.value =
                when (val result = ticketAuthenticator.authenticate(ticket, casBaseUrl)) {
                    is AuthResult.Success -> UiState.Success
                    is AuthResult.Error -> UiState.Error(result.message)
                }
        }
    }
}

sealed class UiState {
    data object Idle : UiState()

    data object Loading : UiState()

    data object Success : UiState()

    data class Error(
        val message: String,
    ) : UiState()
}
