package com.example.gbbank.ui.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gbbank.MainActivity
import com.example.gbbank.databinding.FragmentHomeBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private var lastBalance: Double? = 0.0

    override fun start() {
        realTimeCallBack()
        val activity = requireActivity() as? MainActivity
        activity?.showToolBar()
        listener()
    }



    private fun realTimeCallBack() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.realTimeResponse.collect {
                when(it) {
                    is Resource.Success -> {
                        enableButtons()
                        binding.progressBar.isVisible = false
                        binding.tvName.text = it.data?.firstName
                        binding.tvBalance.text = it.data?.balance.toString()
                        lastBalance = it.data?.balance
                        binding.tvFullName.text = it.data?.firstName?.plus(" ").plus(it.data?.lastName)
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        view?.showSnackBar(it.errorMessage.toString())
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                        disableButtons()
                    }
                }
            }
        }
    }

    private fun listener() {
        binding.btnDeposit.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDepositFragment(
                lastBalance!!.toFloat()
            ))
        }

    }

    private fun disableButtons() {
        with(binding) {
            btnDeposit.isEnabled = false
            btnDeposit.isClickable = false
        }
    }

    private fun enableButtons() {
        with(binding) {
            btnDeposit.isEnabled = true
            btnDeposit.isClickable = true
        }
    }

}