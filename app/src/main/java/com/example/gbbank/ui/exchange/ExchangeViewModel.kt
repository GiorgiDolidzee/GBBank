package com.example.gbbank.ui.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.model.Rates
import com.example.gbbank.repositories.rates_repository.RatesRepositoryImpl
import com.example.gbbank.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val repository: RatesRepositoryImpl
) : ViewModel() {

    val ratesResponse = MutableSharedFlow<Resource<Rates>>()

    fun getRates() = viewModelScope.launch {
        ratesResponse.emit(Resource.Loading())
        withContext(Dispatchers.IO) {
            ratesResponse.emit(
                repository.getRates()
            )
        }
    }

}