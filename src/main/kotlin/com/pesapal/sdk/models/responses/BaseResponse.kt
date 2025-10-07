package com.pesapal.sdk.models.responses

import com.pesapal.sdk.models.common.ErrorResponse

interface BaseResponse {
    val error: ErrorResponse?
    val status: String?
    val message: String?

    fun hasError(): Boolean {
        return error != null || (status != null && status != "200")
    }

    fun getErrorMessage(): String {
        return error?.message ?: message ?: "Unknown error"
    }

    fun getError(): ErrorResponse? = error
}