package com.example.gbbank.model


import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("base")
    val base: String?,
    @SerializedName("commercialRatesList")
    val commercialRatesList: List<CommercialRates?>?
)

data class CommercialRates(
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("buy")
    val buy: Double?,
    @SerializedName("sell")
    val sell: Double?
)
