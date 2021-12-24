package com.example.gbbank.data

import com.example.gbbank.model.ExchangeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApi {

    @GET("/v1/exchange-rates/commercial/convert")
    suspend fun exchange(
        @Query("amount") Amount: Int,
        @Query("from") From: String,
        @Query("to") To: String
    ): Response<ExchangeResponse>
}