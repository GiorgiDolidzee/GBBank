package com.example.gbbank.ui.crypto

import androidx.fragment.app.viewModels
import com.example.gbbank.databinding.FragmentCryptoBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoFragment : BaseFragment<FragmentCryptoBinding>(FragmentCryptoBinding::inflate) {

    private val viewModel: CryptoViewModel by viewModels()

    override fun start() {
        view?.showSnackBar("Crypto Fragment")
    }

}