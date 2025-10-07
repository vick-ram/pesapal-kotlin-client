package com.pesapal.sdk.models.requests

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("consumer_key") val consumerKey: String,
    @SerializedName("consumer_secret") val consumerSecret: String,
)