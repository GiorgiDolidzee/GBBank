package com.example.gbbank.repositories.register_repository

import com.example.gbbank.model.User
import com.example.gbbank.repositories.db_add_user_repository.DbAddUserRepositoryImpl
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val responseHandler: ResponseHandler,
    private val repository: DbAddUserRepositoryImpl
) : RegisterRepository {

    override suspend fun register(firstName: String, lastName: String, email: String, password: String)
    : Resource<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val currentUser = auth.currentUser?.uid
                val defaultPhoto = "https://www.uvu.edu/biology/research/heath_ogden/images/ogdenlab_default.jpg"
                val user = User(firstName, lastName, email, defaultPhoto)
                repository.addUserToDb(currentUser!!, user)
                responseHandler.handleSuccess(result)
            } catch (exception: Exception) {
                responseHandler.handleException(exception)
            }
        }

}