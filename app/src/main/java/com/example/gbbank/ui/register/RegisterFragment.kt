package com.example.gbbank.ui.register

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gbbank.databinding.FragmentRegisterBinding
import com.example.gbbank.extensions.safeNavigate
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun start() {
        listener()
        registration()
    }

    private fun listener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun registration() {
        with(binding) {
            btnSignUp.setOnClickListener {
                val firstName = etFirstName.text.toString()
                val lastName = etLastName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.signUp(firstName, lastName, email, password)
                    viewModel.registerResponse.collect {
                        when (it) {
                            is Resource.Success -> {
                                progressBar.isVisible = false
                                findNavController().safeNavigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
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


}