package com.example.gbbank.repositories.exchange_repository

import com.example.gbbank.data.exchange.ExchangeApi
import com.example.gbbank.model.ExchangeResponse
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(
    private val api: ExchangeApi,
    private val responseHandler: ResponseHandler
) : ExchangeRepository {

    override suspend fun exchange(Amount: Int, From: String, To: String): Resource<ExchangeResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = api.exchange(Amount = Amount, From = From, To = To)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                responseHandler.handleSuccess(body)
            } else {
                responseHandler.handleException(Exception("Unexpected Error Occurred."))

            }
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}