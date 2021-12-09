package com.example.gbbank.ui.login

import androidx.navigation.fragment.findNavController
import com.example.gbbank.databinding.FragmentLoginBinding
import com.example.gbbank.ui.base.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun start() {
        listener()
    }

    private fun listener() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

}