package com.example.gbbank.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.`interface`.FirebaseCallback
import com.example.gbbank.model.User
import com.example.gbbank.repositories.DbCallBackRepository
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.connection.RequestResultCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DbCallBackRepository) : ViewModel() {

    val callBackResponse = MutableSharedFlow<Resource<User?>>()

//    fun getResponse(callback: FirebaseCallback) {
//        viewModelScope.launch {
//            repository.getResponse(callback)
//        }
//
//    }

    fun callBack()
    = viewModelScope.launch {
        callBackResponse.emit(Resource.Loading())
        withContext(Dispatchers.IO) {
            callBackResponse.emit(repository.callBack())
        }
    }

}