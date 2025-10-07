package com.pesapal.sdk.config

data class PesapalConfig(
    val consumerKey: String,
    val consumerSecret: String,
    val baseUrl: String,
    val ipnUrl: String,
    val callbackUrl: String,
    val cancellationUrl: String,
    val autoRefreshToken: Boolean = true,
    val tokenRefreshMargin: Long = 300_000
)