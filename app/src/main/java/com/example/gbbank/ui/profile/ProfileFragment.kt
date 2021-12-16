package com.example.gbbank.ui.profile

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.gbbank.databinding.FragmentProfileBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun start() {
        realTimeCallBack()
    }

    private fun realTimeCallBack() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.realTimeResponse.collect {
                when(it) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        binding.tvFullName.text = it.data?.firstName.plus(" ").plus(it.data?.lastName)
                        binding.tvEmail.text = it.data?.email
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

}