package com.pesapal.sdk.models.common

import com.google.gson.annotations.SerializedName

data class BillingAddress(
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("email_address") val emailAddress: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("line_1") val lineOne: String?,
    @SerializedName("line_2") val lineTwo: String?,
    val city: String?,
    val state: String?,
    @SerializedName("postal_code") val postalCode: String?,
    @SerializedName("zip_code") val zipCode: String?,
)