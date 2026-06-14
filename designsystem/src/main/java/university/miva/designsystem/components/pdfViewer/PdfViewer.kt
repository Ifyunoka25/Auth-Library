package university.miva.designsystem.components.pdfViewer

import Theme
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.ahmer.pdfviewer.PDFView
import com.ahmer.pdfviewer.scroll.DefaultScrollHandle
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import university.miva.designsystem.R
import university.miva.designsystem.components.CustomizeSystemBars
import university.miva.designsystem.components.button.AppBackButton
import university.miva.designsystem.theme.MivaColors
import java.io.File
import java.io.FileOutputStream

@Composable
fun PdfScreen(
    navController: NavController,
    pdfUrl: String,
) {
    val context = LocalContext.current
    var localPdfFile by remember { mutableStateOf<File?>(null) }
    var localContentUri by remember { mutableStateOf<android.net.Uri?>(null) }
    var loadError by remember { mutableStateOf<Throwable?>(null) }

    val fileFromDownload = pdfUrl.startsWith("content://")
    val fileFromUrl = pdfUrl.startsWith("https://")

    val decoded = remember(pdfUrl) { android.net.Uri.decode(pdfUrl) }
    val parsedUri = remember(decoded) { decoded.toUri() }
    val scheme = parsedUri.scheme?.lowercase()

    Timber.tag("PdfScreen").d(
        "pdfUrl: $pdfUrl || fileFromDownload: $fileFromDownload || " +
            "fileFromUrl: $fileFromUrl || scheme: $scheme || parsedUri: $parsedUri",
    )

    LaunchedEffect(pdfUrl) {
        withContext(Dispatchers.IO) {
            try {
                when {
                    fileFromDownload -> {
                        localContentUri = pdfUrl.toUri()
                    }
                    fileFromUrl -> {
                        val file = downloadPdfToCache(context, pdfUrl)
                        if (file != null) {
                            localPdfFile = file
                        } else {
                            Timber.tag("PdfScreen").e("Failed to downloadPdfToCache PDF")
                            loadError = Throwable("Failed to load PDF")
                        }
                    }
                    else -> {
                        throw IllegalArgumentException("Unsupported URI scheme: $scheme")
                    }
                }
            } catch (t: Throwable) {
                loadError = t
            }
        }
    }

    Column {
        when {
            loadError != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CustomizeSystemBars(
                        darkIcons = true,
                    )
                    BasicText(
                        text = "PDF Error: ${loadError?.localizedMessage}",
                        style =
                            Theme.typography.headingM.copy(
                                fontSize = 14.sp,
                                lineHeight = 16.sp,
                            ),
                    )
                }
            }

            localPdfFile != null -> {
                PdfViewer(
                    context = context,
                    navController = navController,
                    file = localPdfFile!!,
                    onError = { e -> loadError = e },
                    onLoad = { Timber.tag("PdfViewer").d("PDF loaded successfully") },
                )
            }

            localContentUri != null -> {
                // Open directly from Download URI
                PdfViewerFromUri(
                    navController = navController,
                    uri = localContentUri!!,
                    onError = { loadError = it },
                    onLoad = { Timber.tag("PdfViewer").d("PDF loaded (URI) successfully") },
                )
            }

            else -> LoadingUi()
        }
    }
}

@Composable
fun PdfViewer(
    context: Context,
    navController: NavController,
    file: File,
    onError: (Throwable) -> Unit = {},
    onLoad: () -> Unit = {},
) {
    Scaffold(
        containerColor = MivaColors.White,
        topBar = {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(color = MivaColors.Secondary.Grey50)
                        .padding(
                            top = Theme.spacing.x10,
                        ),
            ) {
                AppBackButton(
                    onClick = {
                        navController.popBackStack()
                    },
                )
            }
        },
    ) { contentPadding ->
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        top = contentPadding.calculateTopPadding() / 2,
                    ),
            color = MivaColors.White,
        ) {
            CustomizeSystemBars(
                darkIcons = true,
            )

            AndroidView(
                factory = { ctx -> PDFView(ctx, null) },
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(MivaColors.White),
                update = { pdfView ->
                    // Load the PDF from URL
                    try {
                        pdfView
                            .fromFile(file)
                            .scrollHandle(DefaultScrollHandle(context))
                            .enableSwipe(true)
                            .enableAntialiasing(true)
                            .enableDoubleTap(true)
                            .load()
                    } catch (e: Exception) {
                        onError(e)
                    }
                },
            )
        }
    }
}

@Composable
fun PdfViewerFromUri(
    navController: NavController,
    uri: android.net.Uri,
    onError: (Throwable) -> Unit = {},
    onLoad: () -> Unit = {},
) {
    Scaffold(
        containerColor = MivaColors.White,
        topBar = {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(color = MivaColors.Secondary.Grey50)
                        .padding(
                            top = Theme.spacing.x10,
                        ),
            ) {
                AppBackButton(
                    onClick = {
                        navController.popBackStack()
                    },
                )
            }
        },
    ) { contentPadding ->
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        top = contentPadding.calculateTopPadding() / 2,
                    ),
            color = MivaColors.White,
        ) {
            CustomizeSystemBars(
                darkIcons = true,
            )
            AndroidView(
                factory = { ctx -> PDFView(ctx, null) },
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(MivaColors.White),
                update = { pdfView ->
                    try {
                        // load directly from content:// or file://
                        pdfView
                            .fromUri(uri)
                            .scrollHandle(DefaultScrollHandle(pdfView.context))
                            .enableSwipe(true)
                            .enableDoubleTap(true)
                            .enableAntialiasing(true)
                            .load()
                    } catch (e: Exception) {
                        Timber.tag("PdfViewer").e(e, "Error loading PDF from URI")
                        onError(e)
                    }
                },
            )
        }
    }
}

@Composable
fun LoadingUi() {
    Surface(
        color = MivaColors.White,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier =
                    Modifier
                        .height(120.dp)
                        .width(110.dp)
                        .background(MivaColors.Secondary.Transparent),
                contentAlignment = Alignment.Center,
            ) {
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(R.raw.loader_position),
                )

                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    renderMode = RenderMode.SOFTWARE,
                )
            }
        }
    }
}

fun downloadPdfToCache(
    context: Context,
    url: String,
): File? {
    return try {
        val client = OkHttpClient()
        val request =
            Request
                .Builder()
                .url(url)
                .build()
        val response = client.newCall(request).execute()

        if (!response.isSuccessful) return null

        val inputStream = response.body?.byteStream() ?: return null
        val file = File.createTempFile("downloaded_pdf", ".pdf", context.cacheDir)
        val outputStream = FileOutputStream(file)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
