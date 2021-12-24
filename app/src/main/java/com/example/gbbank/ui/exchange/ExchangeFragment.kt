package com.example.gbbank.ui.exchange

import android.graphics.Color
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbbank.adapters.RatesAdapter
import com.example.gbbank.databinding.FragmentExchangeBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.model.ExchangeResponse
import com.example.gbbank.model.Rates
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint

class ExchangeFragment : BaseFragment<FragmentExchangeBinding>(FragmentExchangeBinding::inflate) {

    private val viewModel: ExchangeViewModel by viewModels()
    private lateinit var adapter: RatesAdapter

    override fun start() {
        listeners()
        getRates()
    }

    private fun listeners() {
        binding.btnConvert.setOnClickListener {
            try {
                val amount = binding.etAmount.text.toString().toInt()
                exchange(amount, "usd", "gel")
            } catch (e: Exception) {
                view?.showSnackBar("Please type valid number!")
            }
        }
    }


    private fun getRates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getRates()
            viewModel.ratesResponse.collect {
                when (it) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        rateOperations(it.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        view?.showSnackBar(it.errorMessage!!)
                        Log.d("TAG", it.errorMessage.toString())
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }
    }

    private fun rateOperations(data: Rates?) {
        initRecyclerView(data)
    }

    private fun initRecyclerView(data: Rates?) {
        adapter = RatesAdapter(data)
        binding.rvRates.adapter = adapter
        binding.rvRates.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRates.isNestedScrollingEnabled
    }

    private fun exchange(amount: Int, from: String, to: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.exchange(amount, from, to)
            viewModel.exchangeResponse.collect {
                when (it) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        showExchangeResult(it.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        view?.showSnackBar(it.errorMessage!!)
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }
    }

    private fun showExchangeResult(data: ExchangeResponse? = null) {
        if (data != null) {
            binding.tvResult.text = data.value.toString().plus(" " + data.to)
        }
    }
}