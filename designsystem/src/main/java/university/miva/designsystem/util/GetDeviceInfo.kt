package university.miva.designsystem.util

import androidx.compose.runtime.Composable
import com.devx.kdeviceinfo.OnPlatform
import timber.log.Timber

@Composable
fun GetDeviceInfo() {
    OnPlatform(
        onAndroid = { androidInfo ->
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Android Info: $androidInfo")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2App Name: ${androidInfo.appName}")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Device Name: ${androidInfo.device}")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Device ID: ${androidInfo.id}")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Device Model: ${androidInfo.model}")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Android androidId: ${androidInfo.androidId}")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Device host: ${androidInfo.host}")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Device packageName: ${androidInfo.packageName}")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Android product: ${androidInfo.product}")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Android version baseOs: ${androidInfo.version.baseOs}")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Android version sdkInt: ${androidInfo.version.sdkInt}")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Android version codeName: ${androidInfo.version.codeName}")
            Timber.tag("GetDeviceInfo").d("\uD83D\uDCF2Android version release: ${androidInfo.version.release}")
        },
        onIos = { iosInfo ->
            Timber.tag("GetDeviceInfo").d("📲iOS Info: $iosInfo\n\n")
            Timber.tag("GetDeviceInfo").d("App Name: ${iosInfo.appName}")
            Timber.tag("GetDeviceInfo").d("Device Name: ${iosInfo.name}")
            Timber.tag("GetDeviceInfo").d("Device ID: ${iosInfo.bundleId}")
            Timber.tag("GetDeviceInfo").d("Device Model: ${iosInfo.model}")
            Timber.tag("GetDeviceInfo").d("iOS systemName: ${iosInfo.systemName}")
            Timber.tag("GetDeviceInfo").d("Device packageName: ${iosInfo.systemVersion}")
            Timber.tag("GetDeviceInfo").d("Android version: ${iosInfo.systemVersion}")
        },
        onDesktop = { desktopInfo ->
        },
        onWeb = { webInfo ->
        },
    )
}
