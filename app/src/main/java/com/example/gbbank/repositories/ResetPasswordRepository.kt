package com.example.gbbank.repositories

import android.util.Log.d
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class ResetPasswordRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val responseHandler: ResponseHandler
) {

    suspend fun resetPassword(email: String)
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