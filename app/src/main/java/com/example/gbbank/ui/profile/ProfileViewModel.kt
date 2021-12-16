package com.example.gbbank.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.model.User
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase,
    private val responseHandler: ResponseHandler
) : ViewModel() {


    val realTimeResponse = MutableSharedFlow<Resource<User>>()
    val signOutResponse = MutableSharedFlow<Resource<Unit>>()

    init {
        realTimeCallBack()
    }

    fun realTimeCallBack() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    realTimeResponse.emit(Resource.Loading())
                    val currentUser = auth.currentUser?.uid
                    val dbReference = db.getReference("UserInfo")

                    dbReference.child(currentUser!!).addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            viewModelScope.launch {
                                val user = snapshot.getValue(User::class.java)
                                responseHandler.handleSuccess(realTimeResponse.emit(Resource.Success(user!!)))
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            responseHandler.handleException<Resource<User>>(error.toException())
                        }
                    })

                } catch (e: Exception) {
                    responseHandler.handleException<Resource<User>>(e)
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    signOutResponse.emit(Resource.Loading())
                    responseHandler.handleSuccess(signOutResponse.emit(Resource.Success(auth.signOut())))
                } catch (e: java.lang.Exception) {
                    responseHandler.handleException<Resource<Unit>>(e)
                }
            }
        }
    }

}