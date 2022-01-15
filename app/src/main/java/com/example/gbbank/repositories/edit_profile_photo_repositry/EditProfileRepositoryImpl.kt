package com.example.gbbank.repositories.edit_profile_photo_repositry

import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditProfileRepositoryImpl @Inject constructor(
    private val responseHandler: ResponseHandler,
    private val db: FirebaseDatabase,
    private val auth: FirebaseAuth
) : EditProfileRepository {

    override suspend fun editProfile(url: String): Resource<String> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val dbReference = db.getReference("UserInfo")
                val currentUser = auth.currentUser?.uid
                dbReference.child(currentUser!!).child("photo").setValue(url).await()
                responseHandler.handleSuccess()
            } catch (e: Exception) {
                responseHandler.handleException<String>(e)
            }
        }


}