package university.miva.auth.web

import Theme
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber
import university.miva.auth.api.AuthAnalytics
import university.miva.auth.api.AuthFeatureConfig
import university.miva.auth.api.web.AuthDashboardLauncher
import university.miva.designsystem.R
import university.miva.designsystem.components.loading.LoadingDialog
import university.miva.designsystem.components.snackbar.SnackbarHostWithCustomSnackbar
import university.miva.designsystem.theme.MivaColors
import university.miva.designsystem.util.AppEnvironments
import university.miva.designsystem.util.EnvironmentProvider
import university.miva.designsystem.util.applySecureFlagIfNeeded

private const val DEFAULT_AUTH_URL = "https://miva.university/"
private const val successRedirectPrefix = "https://miva.university/application-submitted/"
private const val loginRedirectPrefix = "miva://login?ticket"

class AuthWebViewActivity : ComponentActivity() {
    private lateinit var viewModel: WebViewModel
    private lateinit var fileChooserCallback: ValueCallback<Array<Uri>>
    private val dashboardLauncher: AuthDashboardLauncher by inject()
    private val envProvider: EnvironmentProvider by inject()
    private val authAnalytics: AuthAnalytics by inject()
    private var ticket: String = ""
    private var isLogoutFlow = false
    private val snackbarHostState = SnackbarHostState()

    private val authFeatureConfig: AuthFeatureConfig by inject()
    private val successLogoutRedirectPrefix = "${authFeatureConfig.casBaseUrl}cas/logout"

    private val fileChooserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val uri = result.data?.data
                fileChooserCallback.onReceiveValue(uri?.let { arrayOf(it) } ?: emptyArray())
            } else {
                fileChooserCallback.onReceiveValue(null)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.applySecureFlagIfNeeded(
            envProvider.environment == AppEnvironments.PRODUCTION,
        )

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        isLogoutFlow = intent.getBooleanExtra(EXTRA_IS_LOGOUT_FLOW, false)

        viewModel = getViewModel()
        val extraUrl = intent.getStringExtra(EXTRA_URL) ?: DEFAULT_AUTH_URL

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            WebViewScreen(
                url = extraUrl,
                uiState = uiState,
                isLogoutFlow = isLogoutFlow,
                snackbarHostState = snackbarHostState,
                onBackPressed = {
                    finish()
                },
                fileChooserLauncher = fileChooserLauncher,
                onFileChooserCallback = { callback ->
                    fileChooserCallback = callback
                },
                onLoginRedirect = { authTicket ->
                    ticket = authTicket
                    lifecycleScope.launch {
                        viewModel.getJwtToken(authTicket, authFeatureConfig.casBaseUrl)
                    }
                },
                onLogoutComplete = {
                    Timber.d("WebViewLog: onLogoutComplete")
                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                        setResult(RESULT_OK)
                    }, 1000)
                    authAnalytics.trackLogoutCompleted()
                },
                onSuccessRedirect = {
                    finish()
                },
                successLogoutRedirectPrefix = successLogoutRedirectPrefix,
            )
        }

        observeUiState()
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is UiState.Success -> {
                        authAnalytics.trackLoginSucceeded()
                        dashboardLauncher.launchDashboard(
                            context = this@AuthWebViewActivity,
                            ticket = ticket,
                            enrollmentStatus = viewModel.getEnrollmentStatus(),
                        )
                        finish()
                    }
                    is UiState.Error -> {
                        lifecycleScope.launch {
                            snackbarHostState.showSnackbar(state.message)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    companion object {
        const val EXTRA_URL = "url"
        const val EXTRA_IS_LOGOUT_FLOW = "isLogoutFlow"
    }
}

@Composable
fun WebViewScreen(
    url: String,
    isLogoutFlow: Boolean,
    uiState: UiState,
    successLogoutRedirectPrefix: String,
    snackbarHostState: SnackbarHostState,
    onBackPressed: (Boolean) -> Unit,
    fileChooserLauncher: ActivityResultLauncher<Intent>,
    onFileChooserCallback: (ValueCallback<Array<Uri>>) -> Unit,
    onLoginRedirect: (String) -> Unit,
    onLogoutComplete: () -> Unit,
    onSuccessRedirect: () -> Unit,
) {
    var showError by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }
    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    Timber.d("WebViewLog: Initial URL provided = $url, isLogoutFlow = $isLogoutFlow")

    Scaffold(
        snackbarHost = {
            SnackbarHostWithCustomSnackbar(snackbarHostState)
        },
    ) { _ ->
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.javaScriptCanOpenWindowsAutomatically = true
                        settings.useWideViewPort = true
                        settings.loadWithOverviewMode = true
                        settings.setSupportMultipleWindows(true)
                        settings.allowFileAccess = true

                        webViewClient =
                            object : WebViewClient() {
                                override fun onLoadResource(
                                    view: WebView?,
                                    url: String?,
                                ) {
                                    super.onLoadResource(view, url)
                                }

                                override fun onPageStarted(
                                    view: WebView?,
                                    url: String?,
                                    favicon: Bitmap?,
                                ) {
                                    Timber.d("WebViewLog: [Page Start] -> $url")
                                    showError = false
                                    progress = 0f
                                }

                                override fun onPageFinished(
                                    view: WebView?,
                                    url: String?,
                                ) {
                                    super.onPageFinished(view, url)

                                    Timber.d("WebViewLog: [Page Finished] -> $url")

                                    if (isLogoutFlow && url != null && url.startsWith(successLogoutRedirectPrefix)) {
                                        Timber.d("WebViewLog: [onPageFinished Logout onLogoutComplete] -> $url")
                                        onLogoutComplete()
                                    }

                                    if (url != null && url.startsWith(successRedirectPrefix)) {
                                        onSuccessRedirect()
                                    }
                                }

                                override fun onReceivedError(
                                    view: WebView?,
                                    request: WebResourceRequest?,
                                    error: WebResourceError?,
                                ) {
                                    val failingUrl = request?.url.toString()
                                    val errorCode = error?.errorCode
                                    val description = error?.description
                                    Timber.d(
                                        "WebViewLog: [Generic Error] Failed to load: $failingUrl. Code: $errorCode, Description: $description",
                                    )

                                    if (request?.isForMainFrame == true) {
                                        showError = true
                                    }
                                }

                                override fun onReceivedHttpError(
                                    view: WebView?,
                                    request: WebResourceRequest?,
                                    errorResponse: WebResourceResponse?,
                                ) {
                                    val failingUrl = request?.url.toString()
                                    val statusCode = errorResponse?.statusCode
                                    Timber.d(
                                        "WebViewLog: [HTTP Error] Failed to load: $failingUrl. Status Code: $statusCode",
                                    )
                                    if (request?.isForMainFrame == true) {
                                        showError = true
                                    }
                                }

                                override fun shouldOverrideUrlLoading(
                                    view: WebView?,
                                    request: WebResourceRequest?,
                                ): Boolean {
                                    val requestUrl = request?.url.toString()

                                    Timber.d("WebViewLog: [URL Override] Intercepted URL -> $requestUrl")

                                    if (requestUrl.startsWith(loginRedirectPrefix)) {
                                        Timber.d("WebViewLog: Detected login redirect.")
                                        val ticket = requestUrl.toUri().getQueryParameter("ticket")
                                        if (ticket != null) {
                                            onLoginRedirect(ticket)
                                            return true
                                        }
                                    }

                                    return false
                                }
                            }

                        webChromeClient =
                            object : WebChromeClient() {
                                override fun onProgressChanged(
                                    view: WebView?,
                                    newProgress: Int,
                                ) {
                                    progress = newProgress / 100f
                                }

                                override fun onShowFileChooser(
                                    webView: WebView?,
                                    filePathCallback: ValueCallback<Array<Uri>>?,
                                    fileChooserParams: FileChooserParams?,
                                ): Boolean {
                                    filePathCallback?.let { callback ->
                                        onFileChooserCallback(callback)
                                        try {
                                            val intent = fileChooserParams?.createIntent()
                                            if (intent != null) {
                                                fileChooserLauncher.launch(intent)
                                                return true
                                            }
                                        } catch (_: ActivityNotFoundException) {
                                            callback.onReceiveValue(null)
                                        }
                                    }
                                    return false
                                }
                            }

                        loadUrl(url)
                        webViewRef = this
                    }
                },
                modifier =
                    Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                update = { webView ->
                    webViewRef = webView
                },
            )

            if (uiState is UiState.Loading) {
                LoadingDialog()
            }

            if (showError) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(MivaColors.White)
                            .padding(Theme.spacing.x4),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.failed_to_load_page),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }
    }

    BackPressHandler {
        if (webViewRef?.canGoBack() == true) {
            webViewRef?.goBack()
        } else {
            onBackPressed(false)
        }
    }
}

@Composable
fun BackPressHandler(onBackPressed: () -> Unit) {
    val backCallback =
        remember {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        }

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    DisposableEffect(backDispatcher) {
        backDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}

fun openAuthInAppWebView(
    context: Context,
    url: String,
    isLogoutFlow: Boolean = false,
) {
    Timber.d("WebViewLog: checkurl $url, isLogoutFlow = $isLogoutFlow")
    val intent =
        Intent(context, AuthWebViewActivity::class.java).apply {
            putExtra(AuthWebViewActivity.EXTRA_URL, url)
            putExtra(AuthWebViewActivity.EXTRA_IS_LOGOUT_FLOW, isLogoutFlow)
        }

    context.startActivity(intent)
}
