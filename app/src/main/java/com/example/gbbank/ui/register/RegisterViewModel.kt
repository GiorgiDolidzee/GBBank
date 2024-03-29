package com.example.gbbank.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.repositories.register_repository.RegisterRepositoryImpl
import com.example.gbbank.utils.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepositoryImpl) : ViewModel() {

    private val _registerResponse = MutableSharedFlow<Resource<AuthResult>>()
    val registerResponse : SharedFlow<Resource<AuthResult>> = _registerResponse

    fun signUp(firstName: String,
               lastName: String,
               email: String,
               password: String)
    =
        viewModelScope.launch {
            _registerResponse.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                _registerResponse.emit(
                    repository.register(
                        firstName, lastName, email, password)
                )
            }
        }

}