package com.example.gbbank.ui.crypto

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gbbank.MainActivity
import com.example.gbbank.adapters.CryptoAdapter
import com.example.gbbank.databinding.FragmentCryptoBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.model.Crypto
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CryptoFragment : BaseFragment<FragmentCryptoBinding>(FragmentCryptoBinding::inflate) {

    private val viewModel: CryptoViewModel by viewModels()
    private lateinit var adapter: CryptoAdapter

    override fun start() {
        val activity = requireActivity() as? MainActivity
        activity?.showToolBar()
        listeners()
        getCrypto()
    }

    private fun listeners(){
        binding.swipeRefresh.setOnRefreshListener {
            start()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun getCrypto() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCrypto()
            viewModel.cryptoResponse.collect {
                when (it) {
                    is Resource.Success -> {
                        binding.animLoading.isVisible = false
                        initRecyclerView(it.data)
                    }
                    is Resource.Error -> {
                        binding.animLoading.isVisible = false
                        view?.showSnackBar(it.errorMessage!!)
                    }
                    is Resource.Loading -> {
                        binding.animLoading.isVisible = true
                        binding.animLoading.playAnimation()
                    }
                }
            }
        }
    }

    private fun initRecyclerView(data: List<Crypto>?) {
        adapter = CryptoAdapter(data!!)
        binding.rvCrypto.adapter = adapter
        binding.rvCrypto.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCrypto.startLayoutAnimation()
        openCryptoFragment()
    }

    private fun openCryptoFragment() {
        adapter.itemClick = {
            findNavController().navigate(CryptoFragmentDirections.actionCryptoFragmentToOpenedCryptoFragment(
                it.name!!,
                it.image!!,
                it.currentPrice!!.toFloat(),
                it.priceChangePercentage24h?.toFloat()!!,
                it.marketCapRank!!,
                it.high24h?.toFloat()!!,
                it.low24h?.toFloat()!!
            ))
        }
    }

}