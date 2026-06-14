package university.miva.designsystem.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun CustomizeSystemBars(
    statusBarColor: Color = Color.Transparent,
    navigationBarColor: Color = Color.Transparent,
    darkIcons: Boolean = false,
) {
    val view = LocalView.current
    val window = (LocalContext.current as Activity).window

    SideEffect {
        window.statusBarColor = statusBarColor.toArgb()
        window.navigationBarColor = navigationBarColor.toArgb()

        val insetsController = WindowInsetsControllerCompat(window, view)
        insetsController.isAppearanceLightStatusBars = darkIcons
        insetsController.isAppearanceLightNavigationBars = darkIcons
    }
}

@Composable
fun CustomizeDashboardSystemBars(
    statusBarColor: Color = Color.Transparent,
    navigationBarColor: Color = Color.Transparent,
    darkIcons: Boolean = false,
) {
    val view = LocalView.current
    val window = (LocalContext.current as Activity).window

    SideEffect {
        window.statusBarColor = statusBarColor.toArgb()
        window.navigationBarColor = navigationBarColor.toArgb()

        val insetsController = WindowInsetsControllerCompat(window, view)
        insetsController.isAppearanceLightStatusBars = darkIcons
        insetsController.isAppearanceLightNavigationBars = true
    }
}
