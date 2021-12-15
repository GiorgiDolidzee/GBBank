package com.example.gbbank.ui.deposit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.repositories.DbAddBalanceRepository
import com.example.gbbank.utils.Resource
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DepositViewModel @Inject constructor(
    private val repository: DbAddBalanceRepository
) : ViewModel() {

    val addBalanceResponse = MutableSharedFlow<Resource<Task<Void>>>()

    fun addBalance(amount: String) =
        viewModelScope.launch {
            addBalanceResponse.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                addBalanceResponse.emit(repository.addBalance(amount))
            }
        }

}