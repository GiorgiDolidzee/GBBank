package com.example.gbbank.ui.getstarted

import android.util.Log.d
import com.example.gbbank.databinding.FragmentGetStartedBinding
import com.example.gbbank.ui.base.BaseFragment


class GetStartedFragment : BaseFragment<FragmentGetStartedBinding>(FragmentGetStartedBinding::inflate) {

//    private val Context.dataStore by preferencesDataStore("userInfo")

    override fun start() {
//        checkIfIsNewUser()
        d("tag", "tag")
//        listener()
    }

//    private fun listener() {
//        binding.btnGetStarted.setOnClickListener {
//            findNavController().navigate(GetStartedFragmentDirections.actionGetStartedFragmentToLoginFragment())
//        }
//    }

//    private fun checkIfIsNewUser() {
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            withContext(IO) {
//                if(read("isNewUser") == "0") {
//                    findNavController().navigate(GetStartedFragmentDirections.actionGetStartedFragmentToLoginFragment())
//                } else {
//                    save("isNewUser", "0")
//                }
//            }
//        }
//    }
//
//    private suspend fun read(key: String) : String? {
//        val dataStoreKey = stringPreferencesKey(key)
//        val it = requireContext().dataStore.data.first()
//        return it[dataStoreKey]
//    }
//
//    private suspend fun save(key: String, value: String) {
//        val dataStoreKey = stringPreferencesKey(key)
//        requireContext().dataStore.edit {
//            it[dataStoreKey] = value
//        }
//    }

}