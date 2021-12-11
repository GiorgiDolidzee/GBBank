package com.example.gbbank.repositories

import com.example.gbbank.model.User
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val responseHandler: ResponseHandler,
    private val repository: DatabaseRepository) {

    suspend fun register(firstName: String, lastName: String, email: String, password: String, repeatPassword: String)
    : Resource<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val currentUser = auth.currentUser?.uid
                val user = User(firstName, lastName, email, 0.0)
                repository.addUserToDb(currentUser!!, user)
                responseHandler.handleSuccess(result)
            } catch (exception: Exception) {
                responseHandler.handleException(exception)
            }
        }

}