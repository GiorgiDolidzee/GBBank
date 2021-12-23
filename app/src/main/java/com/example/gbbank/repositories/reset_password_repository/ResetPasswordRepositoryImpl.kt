package com.example.gbbank.repositories.reset_password_repository

import android.util.Log.d
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

    override suspend fun resetPassword(email: String)
    : Resource<Void> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = auth.sendPasswordResetEmail(email).await()
                d("reset", result.toString())
                responseHandler.handleSuccess(result)
            } catch (e: Exception) {
                responseHandler.handleException(e)
            }
        }

}