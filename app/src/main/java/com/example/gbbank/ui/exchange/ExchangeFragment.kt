package com.example.gbbank.ui.exchange

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbbank.R
import com.example.gbbank.adapters.RatesAdapter
import com.example.gbbank.databinding.FragmentExchangeBinding
import com.example.gbbank.extensions.remove
import com.example.gbbank.extensions.show
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.model.ExchangeResponse
import com.example.gbbank.model.Rates
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExchangeFragment : BaseFragment<FragmentExchangeBinding>(FragmentExchangeBinding::inflate) {

    private val viewModel: ExchangeViewModel by viewModels()
    private lateinit var adapter: RatesAdapter

    override fun start() {
        getRates()
    }


    private fun listeners() {
        binding.ivSwap.setOnClickListener {
            val from = binding.spFrom
            val to = binding.spTo

            val fromPosition = from.selectedItemPosition
            from.setSelection(to.selectedItemPosition)
            to.setSelection(fromPosition)
            exchangeCheck()
        }
        binding.etAmount.addTextChangedListener {
            if (it!!.isEmpty()) {
                binding.llResult.remove()
            } else {
                exchangeCheck()
            }
        }
        spListeners()
    }

    private fun exchangeCheck() {
        try {
            val amount = binding.etAmount.text.toString()
            val from = binding.spFrom.selectedItem.toString()
            val to = binding.spTo.selectedItem.toString()
            if(amount.isNotEmpty()) {
                exchange(amount.toInt(), from, to)
            }
        } catch (e: Exception) {
            view?.showSnackBar(getString(R.string.sb_type_valid_number))

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
                        makeSpinnerArray(it.data)
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
            val from = "${data.amount} ${data.from}"
            val to = "${data.value} ${data.to}"

            binding.tvFrom.text = from
            binding.tvTo.text = to

            binding.llResult.show()
        }
    }

    private fun makeSpinnerArray(data: Rates?) {
        val currencyList = mutableListOf<String>()
        currencyList.add("GEL")
        data?.commercialRatesList?.forEach {
            currencyList.add(it?.currency.toString())
        }
        val currencyArray = currencyList.toTypedArray()
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.layout_spinner_item, currencyArray)


        binding.spFrom.adapter = arrayAdapter
        binding.spTo.adapter = arrayAdapter

        binding.spFrom.setSelection(currencyArray.indexOf("USD"))
        binding.spTo.setSelection(currencyArray.indexOf("GEL"))

        listeners()
    }

    private fun spListeners() {
        binding.spFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                exchangeCheck()
                hideKeyBoard()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }

        binding.spTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                exchangeCheck()
                hideKeyBoard()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }
    }

    private fun hideKeyBoard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}