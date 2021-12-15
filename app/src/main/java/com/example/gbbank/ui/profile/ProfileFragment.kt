package com.example.gbbank.ui.profile

import com.example.gbbank.databinding.FragmentProfileBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun start() {
        view?.showSnackBar("Profile Fragment")
    }

}