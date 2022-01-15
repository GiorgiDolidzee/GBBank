package com.example.gbbank.repositories.register_repository

import com.example.gbbank.utils.Resource
import com.google.firebase.auth.AuthResult

interface RegisterRepository {
    suspend fun register(firstName: String, lastName: String, email: String, password: String) : Resource<AuthResult>
}