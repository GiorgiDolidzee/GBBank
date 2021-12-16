package com.example.gbbank.ui.crypto

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
                        binding.progressBar.isVisible = false
                        initRecyclerView(it.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        view?.showSnackBar(it.errorMessage!!)
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }
    }

    private fun initRecyclerView(data: List<Crypto>?) {
        adapter = CryptoAdapter(data!!)
        binding.rvCrypto.adapter = adapter
        binding.rvCrypto.layoutManager = LinearLayoutManager(requireContext())
    }
}