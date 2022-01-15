package com.example.gbbank.ui.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.repositories.reset_password_repository.ResetPasswordRepositoryImpl
import com.example.gbbank.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val repository: ResetPasswordRepositoryImpl
) : ViewModel() {

    var resetPasswordResponse = MutableSharedFlow<Resource<String>>()

    fun resetPassword(email: String) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                resetPasswordResponse.emit(repository.resetPassword(email))
            }
        }

}