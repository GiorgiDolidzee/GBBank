package com.example.gbbank.ui.register

import androidx.lifecycle.ViewModel
import com.example.gbbank.repositories.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) : ViewModel() {

    fun signUp(firstName: String,
               lastName: String,
               email: String,
               password: String,
               repeatPassword: String
    ) = repository.register(firstName, lastName, email, password, repeatPassword)

}