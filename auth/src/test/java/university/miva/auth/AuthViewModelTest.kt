package university.miva.auth

import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import university.miva.auth.api.data.AuthResult
import university.miva.auth.api.data.AuthSessionStore
import university.miva.auth.api.data.AuthToken
import university.miva.auth.api.data.AuthTokenRefresher
import university.miva.auth.api.data.AuthTokenStore
import university.miva.auth.api.data.AuthUserSession
import university.miva.auth.landing.viewModel.AuthViewModel
import university.miva.auth.landing.viewModel.AuthUiState
import university.miva.designsystem.util.NetworkMonitor

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    private val tokenRefresher = FakeTokenRefresher()
    private val sessionStore = FakeSessionStore()
    private val networkMonitor: NetworkMonitor = mockk(relaxed = true)
    private val tokenStore = FakeTokenStore()

    private lateinit var viewModel: AuthViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { networkMonitor.isOnline } returns flowOf(true)
        viewModel = AuthViewModel(tokenRefresher, sessionStore, networkMonitor, tokenStore)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun exposesCurrentTokenAndSession() {
        assertEquals("access-token", viewModel.getUserToken)
        assertEquals("ENROLLED", viewModel.getCurrentSession()?.enrollmentStatus)
    }

    @Test
    fun refreshTokenSetsSuccessState() =
        runTest(testDispatcher) {
            viewModel.refreshToken("https://sis.example/")

            advanceUntilIdle()

            assertEquals(AuthUiState.Success("Token refreshed"), viewModel.uiState.value)
        }

    @Test
    fun refreshTokenSetsErrorState() =
        runTest(testDispatcher) {
            tokenRefresher.result = AuthResult.Error("Refresh failed")

            viewModel.refreshToken("https://sis.example/")

            advanceUntilIdle()

            assertEquals(AuthUiState.Error("Refresh failed"), viewModel.uiState.value)
        }

    private class FakeTokenRefresher : AuthTokenRefresher {
        var result: AuthResult<Unit> = AuthResult.Success(Unit)

        override suspend fun refreshToken(sisBaseUrl: String): AuthResult<Unit> = result
    }

    private class FakeSessionStore : AuthSessionStore {
        override fun getSession(): AuthUserSession = AuthUserSession(enrollmentStatus = "ENROLLED")

        override fun clear() = Unit
    }

    private class FakeTokenStore : AuthTokenStore {
        override fun getToken(): AuthToken = AuthToken(accessToken = "access-token", refreshToken = "refresh-token")

        override fun setToken(token: AuthToken) = Unit

        override fun clear() = Unit
    }
}
