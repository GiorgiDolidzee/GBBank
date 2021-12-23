package com.example.gbbank.repositories.db_add_user_repository

import com.example.gbbank.model.User
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DbAddUserRepositoryImpl @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val db: FirebaseDatabase
) : DbAddUserRepository {

    override suspend fun addUserToDb(uid: String, user: User)
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