package nz.co.example.rickandmortymodule.features.api.utils

import nz.co.example.coremodule.common.NetworkResult
import retrofit2.Response

internal suspend fun <T> callApi(apiCall: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = apiCall()
        when {
            response.isSuccessful -> {
                response.body()?.let { NetworkResult.Success(it) }
                    ?: NetworkResult.Error(Exception("HTTP 200: Empty response body"))
            }

            else -> NetworkResult.Error(Exception("HTTP Error: ${response.code()} - ${response.message()}"))
        }
    } catch (exception: Exception) {
        handleApiError(exception)
    }
}

private fun <T> handleApiError(exception: Exception): NetworkResult<T> {
    return NetworkResult.Error(exception)
}