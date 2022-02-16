package com.example.gbbank.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.repositories.login_repository.LoginRepositoryImpl
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
class LoginViewModel @Inject constructor(
    private val repository: LoginRepositoryImpl) : ViewModel() {

    private val _loginResponse = MutableSharedFlow<Resource<AuthResult>>()
    val loginResponse : SharedFlow<Resource<AuthResult>> = _loginResponse

    fun signIn(
        email: String,
        password: String,
    ) =
        viewModelScope.launch {
            _loginResponse.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                _loginResponse.emit(
                    repository.login(
                        email,
                        password)
                )
            }
        }


}