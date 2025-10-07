package com.pesapal.sdk.models.responses

import com.google.gson.annotations.SerializedName
import com.pesapal.sdk.models.common.ErrorResponse

data class OrderTransactionResponse(
    @SerializedName("payment_method") val paymentMethod: String,
    val amount: Float,
    @SerializedName("created_date") val createdDate: String,
    @SerializedName("confirmation_code") val confirmationCode: String,
    @SerializedName("payment_status_description") val paymentStatusDescription: String,
    val description: String,
    @SerializedName("payment_account") val paymentAccount: String,
    @SerializedName("call_back_url") val callbackUrl: String,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("merchant_reference") val merchantReference: String,
    @SerializedName("payment_status_code") val paymentStatusCode: String,
    val currency: String,
    override val error: ErrorResponse? = null,
    override val message: String,
    override val status: String
): BaseResponse