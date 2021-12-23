package com.example.gbbank.repositories.login_repository

import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val responseHandler: ResponseHandler) : LoginRepository {

    override suspend fun login(email: String, password: String)
    : Resource<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                responseHandler.handleSuccess(result)
            } catch (e: Exception) {
                responseHandler.handleException(e)
            }
        }
}