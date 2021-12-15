package com.example.gbbank.ui.crypto

import com.example.gbbank.databinding.FragmentCryptoBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment

class CryptoFragment : BaseFragment<FragmentCryptoBinding>(FragmentCryptoBinding::inflate) {

    override fun start() {
        view?.showSnackBar("Crypto Fragment")
    }

}