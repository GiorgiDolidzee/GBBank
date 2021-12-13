package com.example.gbbank.repositories

import androidx.datastore.preferences.protobuf.Value
import com.example.gbbank.model.User
import com.example.gbbank.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase
) {

    private val uid = auth.currentUser!!.uid
//
//    suspend fun getUser() : Resource<User> =
//        withContext(Dispatchers.IO) {
//            return@withContext try {
//                val dbReference = db.getReference("UserInfo")
//                val currentUser = dbReference.child(uid).get().getResult(User::class.java)
//                Resource.Success(currentUser)
//            } catch (e: Exception) {
//                Resource.Error(e.toString())
//            }
//        }

}