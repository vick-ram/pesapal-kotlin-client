package com.pesapal.sdk.models.requests

data class RefundRequest(
    val confirmationCode: String,
    val amount: String,
    val username: String,
    val remarks: String,
)