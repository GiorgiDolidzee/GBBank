package com.example.gbbank.repositories

import androidx.datastore.preferences.protobuf.Value
import com.example.gbbank.model.User
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DbAddUserRepository @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val db: FirebaseDatabase
) {

    suspend fun addUserToDb(uid: String, user: User)
            : Resource<Void> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val dbReference = db.getReference("UserInfo")
                val result = dbReference.child(uid).setValue(user).await()
                responseHandler.handleSuccess(result)
            } catch (e: Exception) {
                responseHandler.handleException(e)
            }
        }

}