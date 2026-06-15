package university.miva.guest.api.data

sealed class GuestDataResult<out T> {
    data class Success<T>(
        val data: T,
    ) : GuestDataResult<T>()

    data class Error(
        val message: String,
    ) : GuestDataResult<Nothing>()
}
