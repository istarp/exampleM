package nz.co.example.coremodule.common

sealed class NetworkResult<out T> {
    data class Success<out R>(val data: R) : NetworkResult<R>()
    data object NoData : NetworkResult<Nothing>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
}