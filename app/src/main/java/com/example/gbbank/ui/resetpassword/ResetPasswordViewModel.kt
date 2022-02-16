package com.example.gbbank.ui.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.repositories.reset_password_repository.ResetPasswordRepositoryImpl
import com.example.gbbank.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val repository: ResetPasswordRepositoryImpl
) : ViewModel() {

    private val _resetPasswordResponse = MutableSharedFlow<Resource<String>>()
    val resetPasswordResponse : SharedFlow<Resource<String>> = _resetPasswordResponse

    fun resetPassword(email: String) =
        viewModelScope.launch {
            _resetPasswordResponse.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                _resetPasswordResponse.emit(repository.resetPassword(email))
            }
        }

}