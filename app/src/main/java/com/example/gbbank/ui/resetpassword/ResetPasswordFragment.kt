package com.example.gbbank.ui.resetpassword

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gbbank.databinding.FragmentResetPasswordBinding
import com.example.gbbank.extensions.safeNavigate
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetPasswordFragment :
    BaseFragment<FragmentResetPasswordBinding>(FragmentResetPasswordBinding::inflate) {

    private val viewModel: ResetPasswordViewModel by viewModels()

    override fun start() {
        listeners()
    }

    private fun listeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnSendResetPassword.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        with(binding) {
            val email = etEmail.text.toString()

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.resetPassword(email)
                viewModel.resetPasswordResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            progressBar.isVisible = false
                            findNavController().safeNavigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment())
                        }
                        is Resource.Error -> {
                            progressBar.isVisible = false
                            view?.showSnackBar(it.errorMessage.toString())
                        }
                        is Resource.Loading -> {
                            progressBar.isVisible = true
                        }
                    }
                }
            }

        }
    }


}