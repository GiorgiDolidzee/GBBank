package com.example.gbbank.repositories

import com.example.gbbank.model.User
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DbAddBalanceRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase,
    private val responseHandler: ResponseHandler,
) {

    suspend fun addBalance(amount: String, currentDate: String) : Resource<Task<Void>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val currentUserUid = auth.currentUser?.uid
                val dbReference = db.getReference("UserInfo")
                val result = dbReference.child(currentUserUid!!).child("balance").setValue(amount.toDouble())
                val transaction = User().transactions
                transaction?.add(User.Transaction(amount, currentDate))
                dbReference.child(currentUserUid).child("transactions").setValue(transaction)
                responseHandler.handleSuccess(result)
            } catch (e: Exception) {
                responseHandler.handleException(e)
            }
        }

}