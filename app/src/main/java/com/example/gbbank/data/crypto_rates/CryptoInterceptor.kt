package com.example.gbbank.data.crypto_rates

import okhttp3.Interceptor
import okhttp3.Response

class CryptoInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .build()
        return chain.proceed(request)
    }
}