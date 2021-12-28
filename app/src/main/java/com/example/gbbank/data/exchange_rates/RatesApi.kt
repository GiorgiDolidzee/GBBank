package com.example.gbbank.data.exchange_rates

import com.example.gbbank.model.Rates
import retrofit2.Response
import retrofit2.http.GET

interface RatesApi {

    @GET("/v1/exchange-rates/commercial")
    suspend fun getRates(): Response<Rates>
}