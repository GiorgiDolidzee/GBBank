package com.example.gbbank.ui.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.model.Crypto
import com.example.gbbank.repositories.crypto_repository.CryptoRepositoryImpl
import com.example.gbbank.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(
    private val repository: CryptoRepositoryImpl
) : ViewModel() {

    val cryptoResponse = MutableSharedFlow<Resource<List<Crypto>>>()

    fun getCrypto() = viewModelScope.launch {
        cryptoResponse.emit(Resource.Loading())
        withContext(Dispatchers.IO) {
            cryptoResponse.emit(
                repository.getCrypto()
            )
        }
    }

}