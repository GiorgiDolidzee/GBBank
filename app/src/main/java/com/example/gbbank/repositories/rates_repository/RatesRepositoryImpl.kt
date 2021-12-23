package com.example.gbbank.repositories.rates_repository

import com.example.gbbank.data.RatesApi
import com.example.gbbank.model.Rates
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val api: RatesApi,
    private val responseHandler: ResponseHandler
) : RatesRepository {

    override suspend fun getRates(): Resource<Rates> =
        withContext(Dispatchers.IO){
            return@withContext try {
                val response = api.getRates()
                val body = response.body()
                if (response.isSuccessful && body != null){
                    responseHandler.handleSuccess(body)
                }else{
                    responseHandler.handleException(Exception("Unexpected Error Occurred"))
                }
            } catch (e: Exception){
                responseHandler.handleException(e)
            }
        }
}