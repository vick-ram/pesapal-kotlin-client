package com.pesapal.sdk.exceptions

import com.pesapal.sdk.models.common.ErrorResponse
import io.ktor.http.HttpStatusCode


class PesapalApiException(
    message: String,
    val statusCode: HttpStatusCode? = null,
    val responseBody: String? = null,
    val error: ErrorResponse? = null
): PesapalException(message)