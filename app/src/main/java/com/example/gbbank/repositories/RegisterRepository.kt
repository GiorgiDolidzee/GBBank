package com.example.gbbank.repositories

import android.view.View
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val responseHandler: ResponseHandler) {

    fun register(firstName: String, lastName: String, email: String, password: String, repeatPassword: String) =
        try {
            val result = auth.createUserWithEmailAndPassword(email, password)
            responseHandler.handleSuccess(result)
        } catch (exception: Exception) {
            responseHandler.handleException<Resource<AuthResult>>(exception)
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