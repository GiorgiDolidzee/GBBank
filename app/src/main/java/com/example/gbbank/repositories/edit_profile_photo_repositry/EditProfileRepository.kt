package com.example.gbbank.repositories.edit_profile_photo_repositry

import com.example.gbbank.utils.Resource

interface EditProfileRepository {
    suspend fun editProfile(url: String): Resource<String>
}