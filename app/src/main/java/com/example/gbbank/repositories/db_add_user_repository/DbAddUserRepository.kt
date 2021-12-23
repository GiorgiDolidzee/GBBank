package com.example.gbbank.repositories.db_add_user_repository

import com.example.gbbank.model.User
import com.example.gbbank.utils.Resource

interface DbAddUserRepository {
    suspend fun addUserToDb(uid: String, user: User): Resource<Void>
}