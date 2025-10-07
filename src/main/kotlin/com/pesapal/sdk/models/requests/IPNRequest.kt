package com.pesapal.sdk.models.requests

import com.google.gson.annotations.SerializedName

data class IPNRequest(
    val url: String,
    @SerializedName("ipn_notification_type") val ipnNotificationType: String
)