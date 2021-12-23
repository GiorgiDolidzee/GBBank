package com.example.gbbank.repositories.crypto_repository

import com.example.gbbank.model.Crypto
import com.example.gbbank.utils.Resource

interface CryptoRepository {
    suspend fun getCrypto(): Resource<List<Crypto>>
}