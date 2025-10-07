package com.pesapal.sdk.exceptions

import com.pesapal.sdk.models.common.ErrorResponse

class PesapalAuthException(
    message: String,
    val error: ErrorResponse? = null
): PesapalException(message)