package com.example.gbbank.utils

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class ResponseHandler {
    fun <T> handleException(e: Exception) : Resource<T> {
        return when(e) {
            is FirebaseAuthInvalidUserException -> Resource.Error("This e-mail is not found")
            is FirebaseAuthUserCollisionException -> Resource.Error("This e-mail already used")
            is FirebaseAuthWeakPasswordException -> Resource.Error("Enter valid password (At least 6 letters)")
            is FirebaseAuthInvalidCredentialsException -> Resource.Error("Enter valid e-mail")
            is FirebaseNetworkException -> Resource.Error("Check your internet connection")
            is IllegalArgumentException -> Resource.Error("Enter valid values")
            is TimeoutException -> Resource.Error("Check your internet connection")
            is UnknownHostException -> Resource.Error("Check your internet connection")
            else -> Resource.Error(e.toString())
        }
    }

    fun <T> handleSuccess(data: T? = null) : Resource<T> {
        return Resource.Success(data)
    }
}