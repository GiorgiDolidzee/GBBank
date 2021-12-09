package com.example.gbbank.ui.resetpassword

import android.widget.Toast
import com.example.gbbank.databinding.FragmentResetPasswordBinding
import com.example.gbbank.ui.base.BaseFragment

class ResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding>(FragmentResetPasswordBinding::inflate) {
    override fun start() {
        Toast.makeText(requireContext(), "ResetPassword Fragment", Toast.LENGTH_SHORT).show()
    }

}