package com.pesapal.sdk.models.responses

import com.pesapal.sdk.models.common.ErrorResponse

data class OrderCancellationResponse(
    override val error: ErrorResponse? = null,
    override val status: String,
    override val message: String
): BaseResponse