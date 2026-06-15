package university.miva.designsystem.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
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
    val activity = LocalContext.current.findActivity()
    val window = activity.window

    SideEffect {
        activity.applySystemBarColors(
            statusBarColor = statusBarColor,
            navigationBarColor = navigationBarColor,
            darkIcons = darkIcons,
        )

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
    val activity = LocalContext.current.findActivity()
    val window = activity.window

    SideEffect {
        activity.applySystemBarColors(
            statusBarColor = statusBarColor,
            navigationBarColor = navigationBarColor,
            darkIcons = darkIcons,
        )

        val insetsController = WindowInsetsControllerCompat(window, view)
        insetsController.isAppearanceLightStatusBars = darkIcons
        insetsController.isAppearanceLightNavigationBars = true
    }
}

private tailrec fun Context.findActivity(): Activity =
    when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> error("Context does not contain an Activity.")
    }

private fun Activity.applySystemBarColors(
    statusBarColor: Color,
    navigationBarColor: Color,
    darkIcons: Boolean,
) {
    val statusColor = statusBarColor.toArgb()
    val navigationColor = navigationBarColor.toArgb()

    if (this is ComponentActivity) {
        enableEdgeToEdge(
            statusBarStyle = statusColor.toSystemBarStyle(darkIcons),
            navigationBarStyle = navigationColor.toSystemBarStyle(darkIcons),
        )
    } else {
        @Suppress("DEPRECATION")
        window.statusBarColor = statusColor
        @Suppress("DEPRECATION")
        window.navigationBarColor = navigationColor
    }
}

private fun Int.toSystemBarStyle(darkIcons: Boolean): SystemBarStyle =
    if (darkIcons) {
        SystemBarStyle.light(scrim = this, darkScrim = this)
    } else {
        SystemBarStyle.dark(scrim = this)
    }
