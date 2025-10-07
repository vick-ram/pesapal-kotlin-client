package com.pesapal.sdk

import com.pesapal.sdk.config.PesapalConfig
import com.pesapal.sdk.constants.PesapalConstants
import com.pesapal.sdk.exceptions.PesapalApiException
import com.pesapal.sdk.exceptions.PesapalAuthException
import com.pesapal.sdk.models.requests.AuthRequest
import com.pesapal.sdk.models.requests.IPNRequest
import com.pesapal.sdk.models.requests.OrderCancellationRequest
import com.pesapal.sdk.models.requests.OrderRequest
import com.pesapal.sdk.models.requests.RefundRequest
import com.pesapal.sdk.models.responses.AuthResponse
import com.pesapal.sdk.models.responses.BaseResponse
import com.pesapal.sdk.models.responses.IPNResponse
import com.pesapal.sdk.models.responses.OrderCancellationResponse
import com.pesapal.sdk.models.responses.OrderResponse
import com.pesapal.sdk.models.responses.OrderTransactionResponse
import com.pesapal.sdk.models.responses.RefundResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class PesapalClient(
    private val httpClient: HttpClient,
    private val config: PesapalConfig
) {
    private var token: String? = null
    private var tokenExpiry: Long = 0
    private val tokenMutex = Mutex()
    private var notificationId: String? = null

    suspend fun ensureAuthenticated() {
        if (config.autoRefreshToken && isTokenExpired()) {
            refreshToken()
        }
    }

    private fun isTokenExpired(): Boolean {
        return token == null || System.currentTimeMillis() > (tokenExpiry - config.tokenRefreshMargin)
    }

    private suspend fun refreshToken(): String = tokenMutex.withLock {
        if (!isTokenExpired()) {
            return token!!
        }

        val response = httpClient.post("${config.baseUrl}/${PesapalConstants.AUTH}") {
            contentType(ContentType.Application.Json)
            setBody(AuthRequest(config.consumerKey, config.consumerSecret))
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                val authResponse = response.body<AuthResponse>()
                if (authResponse.status == "200" && authResponse.token.isNotEmpty()) {
                    token = authResponse.token

                    tokenExpiry = System.currentTimeMillis() + 3_600_000
                    authResponse.token
                } else {
                    throw PesapalAuthException("Authentication failed: ${authResponse.message}", authResponse.error)
                }
            }

            else -> throw PesapalApiException(
                "Failed to authenticate with Pesapal",
                response.status,
                response.bodyAsText()
            )
        }
    }

    suspend fun registerIPN(ipnUrl: String = config.ipnUrl, notificationType: String = "GET"): IPNResponse {
        ensureAuthenticated()
        return httpClient.post("${config.baseUrl}/${PesapalConstants.REGISTER_IPN}") {
            contentType(ContentType.Application.Json)
            bearerAuth(token!!)
            setBody(IPNRequest(ipnUrl, notificationType))
        }.handleResponse()
    }

    suspend fun getOrRegisterIPN(): String {
        notificationId?.let { return it }

        val ipns = getRegisteredIPNs()
        val existingIPN = ipns.find { it.url == config.ipnUrl }

        return if (existingIPN != null) {
            notificationId = existingIPN.ipnId
            existingIPN.ipnId
        } else {
            val response = registerIPN()
            notificationId = response.ipnId
            response.ipnId
        }
    }

    suspend fun getRegisteredIPNs(): List<IPNResponse> {
        ensureAuthenticated()
        return httpClient.get("${config.baseUrl}/${PesapalConstants.REGISTERED_IPN}") {
            bearerAuth(token!!)
        }.handleResponse()
    }

    suspend fun submitOrder(orderRequest: OrderRequest): OrderResponse {
        ensureAuthenticated()
        val notificationId = getOrRegisterIPN()

        val enhancedRequest = orderRequest.copy(
            description = "Payment for products",
            redirectMode = "PARENT_WINDOW",
            notificationId = notificationId,
            callbackUrl = config.callbackUrl,
            cancellationUrl = config.cancellationUrl
        )

        return httpClient.post("${config.baseUrl}/${PesapalConstants.SUBMIT_ORDER}") {
            contentType(ContentType.Application.Json)
            bearerAuth(token!!)
            setBody(enhancedRequest)
        }.handleResponse()
    }

    suspend fun getTransactionStatus(orderTrackingId: String): OrderTransactionResponse {
        ensureAuthenticated()
        return httpClient.get("${config.baseUrl}/${PesapalConstants.TRANSACTION_STATUS}") {
            bearerAuth(token!!)
            parameter("orderTrackingId", orderTrackingId)
        }.handleResponse()
    }

    suspend fun requestRefund(refundRequest: RefundRequest): RefundResponse {
        ensureAuthenticated()
        return httpClient.post("${config.baseUrl}/${PesapalConstants.REQUEST_REFUND}") {
            contentType(ContentType.Application.Json)
            bearerAuth(token!!)
            setBody(refundRequest)
        }.handleResponse()
    }

    suspend fun cancelOrder(orderCancellationRequest: OrderCancellationRequest): OrderCancellationResponse {
        ensureAuthenticated()
        return httpClient.post("${config.baseUrl}/${PesapalConstants.CANCEL_ORDER}") {
            contentType(ContentType.Application.Json)
            bearerAuth(token!!)
            setBody(orderCancellationRequest)
        }.handleResponse()
    }

    private suspend inline fun <reified T> HttpResponse.handleResponse(): T {
        return when (status) {
            HttpStatusCode.OK -> {
                val responseBody: T = body()

                if (responseBody is BaseResponse && responseBody.hasError()) {
                    throw PesapalApiException(
                        message = "Pesapal API error: ${responseBody.getErrorMessage()}",
                        statusCode = status,
                        error = responseBody.error
                    )
                }
                responseBody
            }

            HttpStatusCode.Unauthorized -> {
                if (config.autoRefreshToken) {
                    token = null
                    throw PesapalAuthException("Authentication expired, please retry")
                } else {
                    throw PesapalAuthException("Authentication failed")
                }
            }

            else -> throw PesapalApiException(
                message = "Pesapal API request failed",
                statusCode = status,
                bodyAsText()
            )
        }
    }

    fun clearAuth() {
        token = null
        tokenExpiry = 0
        notificationId = null
    }
}