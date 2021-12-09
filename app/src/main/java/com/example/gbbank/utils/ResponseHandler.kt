package com.example.gbbank.utils

import com.google.firebase.auth.*

class ResponseHandler {
    fun <T> handleException(e: Exception) : Resource<T> {
        return when(e) {
            is FirebaseAuthInvalidUserException -> Resource.Error("Data is invalid")
            is FirebaseAuthUserCollisionException -> Resource.Error("This e-mail already used")
            is FirebaseAuthWeakPasswordException -> Resource.Error("Choose valid password")
            else -> Resource.Error("Error, Try again!")
        }
    }

    fun <T> handleSuccess(data: T) : Resource<T> {
        return Resource.Success(data)
    }
}