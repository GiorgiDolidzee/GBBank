package com.example.gbbank.repositories.add_balance_repository

import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DbAddBalanceRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase,
    private val responseHandler: ResponseHandler,
) : DbAddBalanceRepository {

    override suspend fun addBalance(amount: String, currentDate: String) : Resource<Task<Void>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val currentUserUid = auth.currentUser?.uid
                val dbReference = db.getReference("UserInfo")
                val result = dbReference.child(currentUserUid!!).child("balance").setValue(amount.toDouble())
                responseHandler.handleSuccess(result)
            } catch (e: Exception) {
                responseHandler.handleException(e)
            }
        }

}