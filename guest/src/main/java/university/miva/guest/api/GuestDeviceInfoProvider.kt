package university.miva.guest.api

import kotlinx.coroutines.flow.Flow

interface GuestDeviceInfoProvider {
    val isOnline: Flow<Boolean>

    suspend fun getCountryCode(): String?
}
