package com.example.gbbank.model


import com.google.gson.annotations.SerializedName

data class ExchangeResponse(
    @SerializedName("amount")
    val amount: Double?,
    @SerializedName("from")
    val from: String?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("value")
    val value: Double?
)