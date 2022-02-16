package com.example.gbbank.ui.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.model.ExchangeResponse
import com.example.gbbank.model.Rates
import com.example.gbbank.repositories.exchange_repository.ExchangeRepository
import com.example.gbbank.repositories.exchange_repository.ExchangeRepositoryImpl
import com.example.gbbank.repositories.rates_repository.RatesRepository
import com.example.gbbank.repositories.rates_repository.RatesRepositoryImpl
import com.example.gbbank.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val ratesRepository: RatesRepository,
    private val exchangeRepository: ExchangeRepository
) : ViewModel() {

    private val _ratesResponse = MutableSharedFlow<Resource<Rates>>()
    val ratesResponse : SharedFlow<Resource<Rates>> = _ratesResponse

    private val _exchangeResponse= MutableSharedFlow<Resource<ExchangeResponse>>()
    val exchangeResponse : SharedFlow<Resource<ExchangeResponse>> = _exchangeResponse

    fun getRates() = viewModelScope.launch {
        _ratesResponse.emit(Resource.Loading())
        withContext(Dispatchers.IO) {
            _ratesResponse.emit(
                ratesRepository.getRates()
            )
        }
    }

    fun exchange(Amount: Int, From: String, To: String) = viewModelScope.launch {
        _exchangeResponse.emit(Resource.Loading())
        withContext(Dispatchers.IO) {
            _exchangeResponse. emit(
                exchangeRepository.exchange(Amount, From, To)
            )
        }
    }

}