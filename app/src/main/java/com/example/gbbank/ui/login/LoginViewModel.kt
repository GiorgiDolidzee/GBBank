package com.example.gbbank.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.repositories.LoginRepository
import com.example.gbbank.utils.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
) : ViewModel() {

    val loginResponse = MutableSharedFlow<Resource<AuthResult>>()

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

}