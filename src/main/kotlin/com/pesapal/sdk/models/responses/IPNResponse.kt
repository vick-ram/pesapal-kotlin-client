package com.pesapal.sdk.models.responses

import com.google.gson.annotations.SerializedName
import com.pesapal.sdk.models.common.ErrorResponse

data class IPNResponse(
    val url: String,
    @SerializedName("created_date") val createdDate: String,
    @SerializedName("ipn_id") val ipnId: String,
    @SerializedName("notification_type") val notificationType: Int,
    @SerializedName("ipn_notification_type_description") val ipnNotificationTypeDescription: String,
    @SerializedName("ipn_status") val ipnStatus: Int,
    @SerializedName("ipn_status_description") val ipnStatusDescription: String,
    override val error: ErrorResponse? = null,
    override val message: String,
    override val status: String
): BaseResponse