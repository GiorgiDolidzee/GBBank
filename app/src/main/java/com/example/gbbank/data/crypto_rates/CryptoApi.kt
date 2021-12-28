package com.example.gbbank.data.crypto_rates

import com.example.gbbank.model.Crypto
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("/api/v3/coins/markets")
    suspend fun getCrypto(
        @Query("vs_currency") Currency: String = "usd",
        @Query("pages") Pages: Int = 1,
        @Query("per_page") PerPage: Int = 50,
        @Query("order") Order: String = "market_cap_desc"
    ): Response<List<Crypto>>
}