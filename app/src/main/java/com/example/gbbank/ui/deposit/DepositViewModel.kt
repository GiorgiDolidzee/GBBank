package com.example.gbbank.ui.deposit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbbank.repositories.add_balance_repository.DbAddBalanceRepository
import com.example.gbbank.repositories.add_balance_repository.DbAddBalanceRepositoryImpl
import com.example.gbbank.utils.Resource
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.function.DoubleBinaryOperator
import javax.inject.Inject

@HiltViewModel
class DepositViewModel @Inject constructor(
    private val repository: DbAddBalanceRepository
) : ViewModel() {

    private val _addBalanceResponse = MutableSharedFlow<Resource<Void>>()
    val addBalanceResponse : SharedFlow<Resource<Void>> = _addBalanceResponse

    fun addBalance(oldBalance: Float, deposit: String) =
        viewModelScope.launch {
            _addBalanceResponse.emit(Resource.Loading())
            withContext(Dispatchers.IO) {
                var depositAmount = deposit
                if("," in deposit) { depositAmount = depositAmount.replace(",", ".") }

                if (depositAmount.isNotEmpty() && depositAmount.toDouble() > 0 && depositAmount.length <= 7) {
                    val amount = oldBalance.toDouble() + depositAmount.toDouble()
                    _addBalanceResponse.emit(repository.addBalance(amount))
                } else {
                    _addBalanceResponse.emit(Resource.Error("Enter valid value"))
                }
            }
        }

}