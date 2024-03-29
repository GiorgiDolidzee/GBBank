package com.example.gbbank.ui.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gbbank.MainActivity
import com.example.gbbank.databinding.FragmentLoginBinding
import com.example.gbbank.extensions.safeNavigate
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.hideToolBar()
        listener()
    }

    private fun listener() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        binding.btnSignIn.setOnClickListener {
            login()
        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
        }
    }

    private fun login() {
        with(binding) {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.signIn(email, password)
                viewModel.loginResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            animLoading.isVisible = false
                            findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        }
                        is Resource.Error -> {
                            animLoading.isVisible = false
                            view?.showSnackBar(it.errorMessage.toString())
                        }
                        is Resource.Loading -> {
                            animLoading.isVisible = true
                            animLoading.playAnimation()
                        }
                    }
                }
            }

        }

    }


}