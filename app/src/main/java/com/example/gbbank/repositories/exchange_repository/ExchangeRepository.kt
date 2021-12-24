package com.example.gbbank.repositories.exchange_repository

import com.example.gbbank.model.ExchangeResponse
import com.example.gbbank.utils.Resource

interface ExchangeRepository {
    suspend fun exchange(amount: Int, from: String, to: String): Resource<ExchangeResponse>
}