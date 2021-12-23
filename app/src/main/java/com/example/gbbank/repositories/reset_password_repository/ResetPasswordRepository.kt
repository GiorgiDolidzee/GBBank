package com.example.gbbank.repositories.reset_password_repository

import com.example.gbbank.utils.Resource

interface ResetPasswordRepository {
    suspend fun resetPassword(email: String): Resource<Void>
}