package com.example.gbbank.repositories.rates_repository

import com.example.gbbank.model.Rates
import com.example.gbbank.utils.Resource

interface RatesRepository {
    suspend fun getRates(): Resource<Rates>
}