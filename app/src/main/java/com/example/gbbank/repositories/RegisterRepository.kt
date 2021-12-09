package com.example.gbbank.repositories

import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val responseHandler: ResponseHandler) {

    suspend fun register(firstName: String, lastName: String, email: String, password: String, repeatPassword: String)
    : Resource<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                responseHandler.handleSuccess(result)
            } catch (exception: Exception) {
                responseHandler.handleException(exception)
            }
        }

}

















//            if(firstName.trim().isEmpty() && lastName.trim().isEmpty() && email.trim().isEmpty() && password.trim().isEmpty() && repeatPassword.trim().isEmpty()) {
//                // snackbar
//            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                // snackbar
//            } else if (password.length < 8) {
//                // snackbar
//            } else if (password != repeatPassword) {
//                // snackbar
//            } else {
//                auth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener {
//                        if(it.isSuccessful) {
//                            // action
//                        } else {
//                            // snackbar
//                        }
//                    }
//            }