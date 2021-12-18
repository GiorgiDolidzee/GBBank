package com.example.gbbank.data

import com.example.gbbank.model.Crypto
import com.example.gbbank.model.Rates
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

    @GET("/v1/exchange-rates/commercial")
    suspend fun getRates(): Response<Rates>
}