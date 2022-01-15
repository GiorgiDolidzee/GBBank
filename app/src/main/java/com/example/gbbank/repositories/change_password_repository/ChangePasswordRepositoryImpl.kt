package com.example.gbbank.repositories.change_password_repository

import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangePasswordRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val responseHandler: ResponseHandler
) : ChangePasswordRepository{

    override suspend fun changePassword(password: String) : Resource<String> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                auth.currentUser?.updatePassword(password)
                responseHandler.handleSuccess()
            } catch (e: Exception) {
                responseHandler.handleException<String>(e)
            }
        }

}