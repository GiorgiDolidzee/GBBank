package com.example.gbbank.ui.login

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gbbank.MainActivity
import com.example.gbbank.databinding.FragmentLoginBinding
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
        checkIfLogged()
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

    private fun checkIfLogged() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.logged.collect {
                when(it) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        if(it.data == false) {
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        }
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

    private fun login() {
        with(binding) {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.signIn(email, password)
                viewModel.loginResponse.collect {
                    when (it) {
                        is Resource.Success -> {
                            progressBar.isVisible = false
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
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