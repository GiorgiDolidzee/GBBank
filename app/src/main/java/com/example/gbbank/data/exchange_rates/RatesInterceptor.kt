package com.example.gbbank.data.exchange_rates

import com.example.gbbank.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RatesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("apikey", BuildConfig.API_KEY)
            .build()
        return chain.proceed(request)
    }
}