package com.example.gbbank.repositories.add_balance_repository

import com.example.gbbank.utils.Resource
import com.google.android.gms.tasks.Task

interface DbAddBalanceRepository {
    suspend fun addBalance(amount: Double) : Resource<Void>
}