package com.example.gbbank.ui.deposit

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gbbank.MainActivity
import com.example.gbbank.R
import com.example.gbbank.databinding.FragmentDepositBinding
import com.example.gbbank.extensions.safeNavigate
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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


    private fun addToBalance(deposit: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            val oldBalance = args.oldBalance
            viewModel.addBalance(oldBalance, deposit)
            viewModel.addBalanceResponse.collect {
                when(it) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        binding.animSuccessful.isVisible = true
                        binding.animBackground.isVisible = true
                        binding.animSuccessful.playAnimation()
                        delay(500)
                        view?.showSnackBar("\uD83D\uDE80 $deposit₾ added to balance")
                        playSound()
                        delay(1000)
                        binding.animSuccessful.isVisible = false
                        binding.animBackground.isVisible = false
                        findNavController().safeNavigate(DepositFragmentDirections.actionDepositFragmentToHomeFragment())
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


    private fun playSound() {
        val sound = MediaPlayer.create(requireContext(), R.raw.donesound)
        sound.start()
    }

}