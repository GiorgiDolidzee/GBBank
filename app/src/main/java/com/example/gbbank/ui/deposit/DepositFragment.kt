package com.example.gbbank.ui.deposit

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gbbank.utils.Resource
import com.example.gbbank.databinding.FragmentDepositBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DepositFragment : BaseFragment<FragmentDepositBinding>(FragmentDepositBinding::inflate) {

    private val viewModel: DepositViewModel by viewModels()

    override fun start() {
        listener()
    }

    private fun listener() {
        binding.btnAddToBalance.setOnClickListener {
            val amount = binding.etAmount.text.toString()
            addToBalance(amount)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(DepositFragmentDirections.actionDepositFragmentToHomeFragment())
        }
    }

    private fun addToBalance(amount: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addBalance(amount.trim())
            viewModel.addBalanceResponse.collect {
                when(it) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        view?.showSnackBar("$amount₾ added to balance")
                        findNavController().navigate(DepositFragmentDirections.actionDepositFragmentToHomeFragment())
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        view?.showSnackBar(it.errorMessage.toString())
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }

    }

}