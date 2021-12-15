package com.example.gbbank.ui.exchange

import com.example.gbbank.databinding.FragmentExchangeBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment

class ExchangeFragment : BaseFragment<FragmentExchangeBinding>(FragmentExchangeBinding::inflate) {

    override fun start() {
        view?.showSnackBar("Exchange Fragment")
    }

}