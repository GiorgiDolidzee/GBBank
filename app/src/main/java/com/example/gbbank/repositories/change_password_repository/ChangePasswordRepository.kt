package com.example.gbbank.repositories.change_password_repository

import com.example.gbbank.utils.Resource

interface ChangePasswordRepository {
    suspend fun changePassword(password: String): Resource<String>
}