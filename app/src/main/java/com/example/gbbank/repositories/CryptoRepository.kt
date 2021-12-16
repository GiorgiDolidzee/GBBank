package com.example.gbbank.repositories

import com.example.gbbank.data.CryptoApi
import com.example.gbbank.model.Crypto
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val api: CryptoApi,
    private val responseHandler: ResponseHandler
) {
    suspend fun getCrypto(): Resource<List<Crypto>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val response = api.getCrypto()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    responseHandler.handleSuccess(body)
                } else{
                    responseHandler.handleException(Exception("$response."))
                }
            } catch (e: Exception) {
                responseHandler.handleException(e)
            }
        }
}