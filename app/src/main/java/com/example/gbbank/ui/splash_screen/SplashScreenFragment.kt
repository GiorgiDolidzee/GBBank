package com.example.gbbank.ui.splash_screen

import android.annotation.SuppressLint
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gbbank.R
import com.example.gbbank.databinding.SplashScreenFragmentBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : BaseFragment<SplashScreenFragmentBinding>(SplashScreenFragmentBinding::inflate) {

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun start() {
        viewLifecycleOwner.lifecycleScope.launch {
            setAnimation()
            delay(2500)
            checkIfLogged()
        }
    }

    private fun setAnimation() {
        binding.ivLogo.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.scale_up))
    }

    private suspend fun checkIfLogged() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.logged.collect {
                when(it) {
                    is Resource.Success -> {
                        if(it.data == false) {
                            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment())
                        } else {
                            findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment())
                        }
                    }
                    is Resource.Error -> {
                        view?.showSnackBar(it.errorMessage!!)
                    }
                }
            }
        }
    }


}