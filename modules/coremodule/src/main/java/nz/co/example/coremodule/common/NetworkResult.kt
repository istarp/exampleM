package nz.co.example.coremodule.common

sealed class NetworkResult<out T> {
    data object NoData : NetworkResult<Nothing>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
    data class Success<out R>(val value: R) : NetworkResult<R>()
}