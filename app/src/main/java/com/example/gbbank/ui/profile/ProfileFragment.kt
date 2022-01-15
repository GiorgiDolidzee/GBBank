package com.example.gbbank.ui.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.gbbank.R
import com.example.gbbank.databinding.FragmentProfileBinding
import com.example.gbbank.extensions.setImage
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
        listeners()
    }

    @SuppressLint("InflateParams")
    private fun listeners() {
        binding.btnSignOut.setOnClickListener {
            signOut()
        }
        binding.btnEdit.setOnClickListener {
            editProfilePhoto()
        }

    }

    private fun realTimeCallBack() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.realTimeResponse.collect {
                when(it) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        binding.tvFullName.text = it.data?.firstName.plus(" ").plus(it.data?.lastName)
                        binding.tvEmail.text = it.data?.email
                        binding.circleImageView.setImage(it.data?.photo)
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

    @SuppressLint("InflateParams")
    private fun editProfilePhoto() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.layout_edit_photo, null)

        val url = dialogLayout.findViewById<EditText>(R.id.etUrl)

        with(builder) {
            setTitle("Edit profile")
            setPositiveButton("Save"){ dialog, which ->
                saveProfilePhoto(url.text.toString())
            }
            setNegativeButton("Cancel") { dialog, which -> }

            setView(dialogLayout)
            show()
        }
    }

    private fun saveProfilePhoto(url: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editProfile(url)
            viewModel.editProfileResponse.collect {
                when(it) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
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

    private fun signOut() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signOut()
            viewModel.signOutResponse.collect {
                when(it) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
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