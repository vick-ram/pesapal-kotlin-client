package com.pesapal.sdk.models.requests

import com.google.gson.annotations.SerializedName
import com.pesapal.sdk.models.common.BillingAddress

data class OrderRequest(
    val id: String,
    val currency: String,
    val amount: Float,
    val description: String,
    @SerializedName("redirect_mode") val redirectMode: String?,
    @SerializedName("callback_url") val callbackUrl: String,
    @SerializedName("cancellation_url") val cancellationUrl: String,
    @SerializedName("notification_id") val notificationId: String,
    val branch: String?,
    val billingAddress: BillingAddress,
)