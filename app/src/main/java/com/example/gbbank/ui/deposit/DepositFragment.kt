package com.example.gbbank.ui.deposit

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gbbank.MainActivity
import com.example.gbbank.databinding.FragmentDepositBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DepositFragment : BaseFragment<FragmentDepositBinding>(FragmentDepositBinding::inflate) {

    private val viewModel: DepositViewModel by viewModels()
    private val args: DepositFragmentArgs by navArgs()

    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.hideToolBar()
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

    @SuppressLint("SimpleDateFormat")
    private fun addToBalance(amount: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            val newBalance: Double = args.oldBalance.toDouble() + amount.toDouble()
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            viewModel.addBalance(newBalance.toString().trim(), currentDate)
            viewModel.addBalanceResponse.collect {
                when(it) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        view?.showSnackBar("\uD83D\uDE80 $amount₾ added to balance")
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