package com.example.gbbank.ui.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.model.Crypto
import com.example.gbbank.repositories.CryptoRepository
import com.example.gbbank.utils.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    val cryptoResponse = MutableSharedFlow<Resource<List<Crypto>>>()


    fun getCrypto() = viewModelScope.launch {
        cryptoResponse.emit(Resource.Loading())
        withContext(Dispatchers.IO){
            cryptoResponse.emit(
                repository.getCrypto()
            )
        }
    }

}