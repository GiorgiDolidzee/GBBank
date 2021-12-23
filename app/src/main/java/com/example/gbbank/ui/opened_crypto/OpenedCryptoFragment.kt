package com.example.gbbank.ui.opened_crypto

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gbbank.MainActivity
import com.example.gbbank.databinding.FragmentOpenedCryptoBinding
import com.example.gbbank.extensions.setImage
import com.example.gbbank.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpenedCryptoFragment : BaseFragment<FragmentOpenedCryptoBinding>(FragmentOpenedCryptoBinding::inflate) {

    private val args: OpenedCryptoFragmentArgs by navArgs()

    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.hideToolBar()
        getArgs()
        listener()
    }

    private fun getArgs() {
        with(binding) {
            tvCrypto.text = args.cryptoName
            ivCrypto.setImage(args.cryptoImg)
            tvPrice.text = args.price.toString().plus("$")
            tvChangePercent.text = args.changePercent.toString().plus("%")
            tvRank.text = args.rank.toString()
            tvHigh.text = args.highPrice.toString().plus("$")
            tvLow.text = args.lowPrice.toString().plus("$")
        }
    }

    private fun listener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.swipeRefresh.setOnRefreshListener {
            start()
            binding.swipeRefresh.isRefreshing = false
        }

    }

}