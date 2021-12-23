package com.example.gbbank.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.repositories.login_repository.LoginRepositoryImpl
import com.example.gbbank.utils.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepositoryImpl,
    private val auth: FirebaseAuth
) : ViewModel() {

    val loginResponse = MutableSharedFlow<Resource<AuthResult>>()
    var logged = MutableSharedFlow<Resource<Boolean>>()

    init {
        checkIfLoggedIn()
    }

    fun signIn(
        email: String,
        password: String,
    ) =
        viewModelScope.launch {
            loginResponse.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                loginResponse.emit(
                    repository.login(
                        email,
                        password)
                )
            }
        }

    private fun checkIfLoggedIn() {
        viewModelScope.launch {
            logged.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                try {
                    logged.emit(Resource.Success(auth.currentUser==null))
                } catch (e: Exception) {
                    logged.emit(Resource.Error(e.toString()))
                }
            }
        }
    }

}