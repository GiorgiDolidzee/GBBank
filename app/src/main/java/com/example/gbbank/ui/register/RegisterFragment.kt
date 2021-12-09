package com.example.gbbank.ui.register

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.gbbank.databinding.FragmentRegisterBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.repositories.RegisterRepository
import com.example.gbbank.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun start() {
        listener()
    }

    private fun listener() {

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSignUp.setOnClickListener {
            registration()
        }

    }


    private fun registration() {

        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val repeatPassword = binding.etRepeatPassword.text.toString()

        if(firstName.trim().isEmpty() || lastName.trim().isEmpty() ||
            email.trim().isEmpty() || password.trim().isEmpty() ||
            repeatPassword.trim().isEmpty()) {
            view?.showSnackBar("Fill all the fields", Snackbar.LENGTH_LONG)
        } else {
            viewModel.signUp(firstName, lastName, email, password, repeatPassword)
            findNavController().popBackStack()
        }

    }

}