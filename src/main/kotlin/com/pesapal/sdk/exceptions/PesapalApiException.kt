package com.pesapal.sdk.exceptions

import com.pesapal.sdk.models.common.ErrorResponse
import io.ktor.http.HttpStatusCode


class PesapalApiException(
    message: String,
    statusCode: HttpStatusCode? = null,
    responseBody: String? = null,
    val error: ErrorResponse? = null
): PesapalException(message)