package university.miva.designsystem.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

const val DEFAULT_COUNTRY_CODE = "NG"

class LocationHelper(
    private val context: Context,
) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    companion object {
        private const val TAG = "LocationHelper"
    }

    /**
     * Checks if the necessary location permissions are granted.
     *
     * @return true if either ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION is granted, false otherwise.
     * This method does NOT request permissions; permission requests must be handled
     * by the calling Activity or Fragment.
     */
    private fun hasLocationPermissions(): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED

    suspend fun getCountryCode(): String? {
        if (!hasLocationPermissions()) {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val countryCode = telephonyManager.simCountryIso.uppercase()
            return countryCode
        }

        try {
            // Get the last known location asynchronously using coroutines
            val location: Location? =
                withContext(Dispatchers.IO) {
                    fusedLocationClient.lastLocation.await()
                }

            if (location == null) {
                return Locale.getDefault().country
            }

            // Perform geocoding on an IO dispatcher to avoid blocking the main thread
            return getCountryCodeFromLocation(location)
        } catch (e: SecurityException) {
            return null
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * Performs reverse geocoding to get the country code from a given Location object.
     * Runs on Dispatchers.IO.
     */
    private suspend fun getCountryCodeFromLocation(location: Location): String? =
        withContext(Dispatchers.IO) {
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                // maxResults = 1 because we only need the first (most relevant) address
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val countryCode = addresses[0].countryCode
                    countryCode
                } else {
                    null
                }
            } catch (e: IOException) {
                null
            } catch (e: IllegalArgumentException) {
                null
            } catch (e: Exception) {
                null
            }
        }
}
