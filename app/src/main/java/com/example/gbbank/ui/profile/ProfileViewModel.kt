package com.example.gbbank.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.model.User
import com.example.gbbank.repositories.change_password_repository.ChangePasswordRepository
import com.example.gbbank.repositories.change_password_repository.ChangePasswordRepositoryImpl
import com.example.gbbank.repositories.edit_profile_photo_repositry.EditProfileRepository
import com.example.gbbank.repositories.edit_profile_photo_repositry.EditProfileRepositoryImpl
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
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseDatabase,
    private val responseHandler: ResponseHandler,
    private val editProfileRepository: EditProfileRepository,
    private val changePasswordRepository: ChangePasswordRepository
) : ViewModel() {

    private val _realTimeResponse = MutableSharedFlow<Resource<User>>()
    val realTimeResponse : SharedFlow<Resource<User>> = _realTimeResponse

    private val _signOutResponse = MutableSharedFlow<Resource<Unit>>()
    val signOutResponse : SharedFlow<Resource<Unit>> = _signOutResponse

    private val _editProfileResponse = MutableSharedFlow<Resource<String>>()
    val editProfileResponse : SharedFlow<Resource<String>> = _editProfileResponse

    private val _changePasswordResponse = MutableSharedFlow<Resource<String>>()
    val changePasswordResponse : SharedFlow<Resource<String>> = _changePasswordResponse


    fun realTimeCallBack() {
        viewModelScope.launch {
            _realTimeResponse.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                try {
                    val currentUser = auth.currentUser?.uid
                    val dbReference = db.getReference("UserInfo")

                    dbReference.child(currentUser!!).addValueEventListener(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            viewModelScope.launch {
                                val user = snapshot.getValue(User::class.java)
                                responseHandler.handleSuccess(_realTimeResponse.emit(Resource.Success(user!!)))
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


    fun editProfile(url: String) {
        viewModelScope.launch {
            _editProfileResponse.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                try {
                    _editProfileResponse.emit(editProfileRepository.editProfile(url))
                } catch (e: java.lang.Exception) {
                    _editProfileResponse.emit(responseHandler.handleException(e))
                }
            }
        }
    }


    fun changePassword(password: String) {
        viewModelScope.launch {
            _changePasswordResponse.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                try {
                    _changePasswordResponse.emit(changePasswordRepository.changePassword(password))
                } catch (e: java.lang.Exception) {
                    _changePasswordResponse.emit(responseHandler.handleException(e))
                }
            }
        }
    }


    fun signOut() {
        viewModelScope.launch {
            _signOutResponse.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                try {
                    responseHandler.handleSuccess(_signOutResponse.emit(Resource.Success(auth.signOut())))
                } catch (e: java.lang.Exception) {
                    responseHandler.handleException<Resource<Unit>>(e)
                }
            }
        }
    }


}