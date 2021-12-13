package com.example.gbbank.repositories

import android.util.Log.d
import com.example.gbbank.`interface`.FirebaseCallback
import com.example.gbbank.model.Response
import com.example.gbbank.model.User
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DbCallBackRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase,
    private val responseHandler: ResponseHandler,
) {

//    suspend fun getResponse(callback: FirebaseCallback) {
//        withContext(Dispatchers.IO) {
//            val dbReference = db.getReference("UserInfo")
//            val currentUser = auth.currentUser?.uid
//
//            dbReference.child(currentUser!!).get().addOnCompleteListener {
//                val response = Response()
//                if(it.isSuccessful) {
//                    val result = it.result
//                    result?.let {
//                        response.user = result.getValue(User::class.java)
//                    }
//                }
//                callback.onResponse(response)
//            }
//        }
//    }

    private lateinit var user: User

    suspend fun callBack() : Resource<User?> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val dbReference = db.getReference("UserInfo")
                val currentUser = auth.currentUser?.uid

                dbReference.child(currentUser!!).addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val data = snapshot.getValue(User::class.java)
                        d("repository-data", data.toString())
                        user = User(data?.firstName, data?.lastName, data?.email, data?.balance)
                        responseHandler.handleSuccess(user)
                    }
                    override fun onCancelled(error: DatabaseError) {
                        responseHandler.handleException<Resource<User>>(error.toException())
                    }
                })
                d("repository-user", user.toString())
                responseHandler.handleSuccess(user)

            } catch (exception: Exception) {
                responseHandler.handleException(exception)
            }
        }

}