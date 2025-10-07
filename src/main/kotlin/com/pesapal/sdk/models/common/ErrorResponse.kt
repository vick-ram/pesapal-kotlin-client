package com.pesapal.sdk.models.common

data class ErrorResponse(
    val type: String,
    val code: String,
    val message: String
)