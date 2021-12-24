package com.example.gbbank.ui.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.model.ExchangeResponse
import com.example.gbbank.model.Rates
import com.example.gbbank.repositories.exchange_repository.ExchangeRepository
import com.example.gbbank.repositories.exchange_repository.ExchangeRepositoryImpl
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
    private val ratesRepository: RatesRepositoryImpl,
    private val exchangeRepository: ExchangeRepositoryImpl
) : ViewModel() {

    val ratesResponse = MutableSharedFlow<Resource<Rates>>()
    val exchangeResponse= MutableSharedFlow<Resource<ExchangeResponse>>()

    fun getRates() = viewModelScope.launch {
        ratesResponse.emit(Resource.Loading())
        withContext(Dispatchers.IO) {
            ratesResponse.emit(
                ratesRepository.getRates()
            )
        }
    }

    fun exchange(Amount: Int, From: String, To: String) = viewModelScope.launch {
        exchangeResponse.emit(Resource.Loading())
        withContext(Dispatchers.IO) {
            exchangeResponse. emit(
                exchangeRepository.exchange(Amount = Amount, From = From, To = To)
            )
        }
    }

}