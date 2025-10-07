package com.pesapal.sdk.models.responses

import com.google.gson.annotations.SerializedName
import com.pesapal.sdk.models.common.ErrorResponse

data class OrderResponse(
    @SerializedName("order_tracking_id") val orderTrackingId: String,
    @SerializedName("merchant_reference") val merchantReference: String,
    @SerializedName("redirect_url") val redirectUrl: String,
    override val error: ErrorResponse? = null,
    override val message: String,
    override val status: String
): BaseResponse