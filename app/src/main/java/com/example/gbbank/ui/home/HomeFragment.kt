package com.example.gbbank.ui.home

import android.util.Log.d
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gbbank.`interface`.FirebaseCallback
import com.example.gbbank.databinding.FragmentHomeBinding
import com.example.gbbank.extensions.showSnackBar
import com.example.gbbank.model.Response
import com.example.gbbank.model.User
import com.example.gbbank.ui.base.BaseFragment
import com.example.gbbank.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun start() {
        listener()
        realTimeCallBack()
//        getResponse()
    }

    private fun listener() {
        binding.btnDeposit.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDepositFragment())
        }
    }


//    private fun getResponse() {
//        viewModel.getResponse(object: FirebaseCallback {
//            override fun onResponse(response: Response) {
//                d("response", response.toString())
//                binding.tvName.text = response.user?.firstName
//                binding.tvFullName.text = response.user?.firstName?.plus(" ").plus(response.user?.lastName)
//                binding.tvBalance.text = response.user?.balance.toString()
//            }
//
//        })
//    }

    private fun realTimeCallBack() {
        viewLifecycleOwner.lifecycleScope.launch {
            with(binding) {
                viewModel.callBack()
                viewModel.callBackResponse.collect {
                    when(it) {
                        is Resource.Success -> {
                            progressBar.isVisible = false
                            tvName.text = it.data?.firstName
                            tvBalance.text = it.data?.balance.toString()
                            tvFullName.text = it.data?.firstName?.plus(" ").plus(it.data?.lastName)
                        }
                        is Resource.Error -> {
                            progressBar.isVisible = false
                            view?.showSnackBar(it.errorMessage.toString())
                        }
                        is Resource.Loading -> {
                            progressBar.isVisible = true
                        }
                    }
                }
            }
        }
    }

}