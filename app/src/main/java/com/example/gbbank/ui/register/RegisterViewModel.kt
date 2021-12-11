package com.example.gbbank.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.repositories.RegisterRepository
import com.example.gbbank.utils.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) : ViewModel() {

    val registerResponse = MutableSharedFlow<Resource<AuthResult>>()

    fun signUp(firstName: String,
               lastName: String,
               email: String,
               password: String,
               repeatPassword: String
    ) =
        viewModelScope.launch {
            registerResponse.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                registerResponse.emit(
                    repository.register(
                        firstName,
                        lastName,
                        email,
                        password,
                        repeatPassword)
                )
            }
        }

}