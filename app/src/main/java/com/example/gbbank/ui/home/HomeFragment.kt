package com.example.gbbank.ui.home

import com.example.gbbank.databinding.FragmentHomeBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun start() {
        view?.showSnackBar("gio magaria")
    }

}