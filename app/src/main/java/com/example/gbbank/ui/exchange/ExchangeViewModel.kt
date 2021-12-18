package com.example.gbbank.ui.exchange

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gbbank.adapters.RatesAdapter
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.model.Rates
import com.example.gbbank.repositories.RatesRepository
import com.example.gbbank.utils.Resource
import com.example.gbbank.utils.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val repository: RatesRepository
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

    fun convert(from: String, to: String, strAmount: String, rates: Rates?) {
    }




}