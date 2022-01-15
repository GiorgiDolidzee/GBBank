package com.example.gbbank.repositories.reset_password_repository

import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResetPasswordRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val responseHandler: ResponseHandler
) : ResetPasswordRepository {

    override suspend fun resetPassword(email: String): Resource<String> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                auth.sendPasswordResetEmail(email).await()
                responseHandler.handleSuccess()
            } catch (e: Exception) {
                responseHandler.handleException(e)
            }
        }

}