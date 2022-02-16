package com.example.gbbank.data.exchange

import com.example.gbbank.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ExchangeInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("apikey", BuildConfig.EXCHANGE_API_KEY)
            .build()
        return chain.proceed(request)
    }

}