package com.example.gbbank.repositories.login_repository

import com.example.gbbank.utils.Resource
import com.google.firebase.auth.AuthResult

interface LoginRepository {
    suspend fun login(email: String, password: String): Resource<AuthResult>
}