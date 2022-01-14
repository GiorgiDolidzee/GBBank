package com.example.gbbank.ui.splash_screen

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth): ViewModel() {

    var logged = MutableSharedFlow<Resource<Boolean>>()

    init {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        viewModelScope.launch {
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