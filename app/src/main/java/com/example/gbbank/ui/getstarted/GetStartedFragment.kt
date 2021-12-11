package com.example.gbbank.ui.getstarted

import android.content.Context
import android.view.animation.AnimationUtils
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gbbank.R
import com.example.gbbank.databinding.FragmentGetStartedBinding
import com.example.gbbank.ui.base.BaseFragment
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.prefs.Preferences


class GetStartedFragment : BaseFragment<FragmentGetStartedBinding>(FragmentGetStartedBinding::inflate) {

//    private val Context.dataStore by preferencesDataStore("userInfo")

    override fun start() {
//        checkIfIsNewUser()
        listener()
    }

    private fun listener() {
        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(GetStartedFragmentDirections.actionGetStartedFragmentToLoginFragment())
        }
    }

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