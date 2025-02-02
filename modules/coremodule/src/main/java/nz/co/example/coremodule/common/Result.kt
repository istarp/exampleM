package nz.co.example.coremodule.common

sealed class Result<out T> {
    data object Loading : Result<Nothing>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class Data<out R>(val data: R) : Result<R>()
}