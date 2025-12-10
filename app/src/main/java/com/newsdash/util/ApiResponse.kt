package com.newsdash.util

sealed class ApiResponse {
    data class Success(val data: Any?) : ApiResponse()
    data class Error(val message: String? = null) : ApiResponse()
    object Loading : ApiResponse()
}
