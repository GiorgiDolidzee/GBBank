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

    private fun listeners() {
        binding.btnEdit.setOnClickListener {
            editProfilePhoto()
        }

        binding.btnChangePassword.setOnClickListener {
            changePassword()
        }

        binding.btnAppInformation.setOnClickListener {
            showAppInformation()
        }

        binding.btnSignOut.setOnClickListener {
            signOut()
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
            setTitle(getString(R.string.edit_profile))
            setPositiveButton(getString(R.string.save)){ dialog, which ->
                saveProfilePhoto(url.text.toString())
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, which -> }

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
                        view?.showSnackBar("\uD83D\uDCCE ${getString(R.string.profile_photo_changed)}")
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

    @SuppressLint("InflateParams")
    private fun changePassword() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.layout_change_password, null)

        val password = dialogLayout.findViewById<EditText>(R.id.etNewPassword)

        with(builder) {
            setTitle(getString(R.string.change_password))
            setPositiveButton(getString(R.string.change)){ dialog, which ->
                savePassword(password.text.toString())
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, which -> }

            setView(dialogLayout)
            show()
        }
    }

    private fun savePassword(password: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.changePassword(password)
            viewModel.changePasswordResponse.collect {
                when(it) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        view?.showSnackBar("\uD83D\uDD12 ${getString(R.string.password_changed)}")
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

    private fun showAppInformation() {
        val message = "${getString(R.string.gbbank_description)} \n \n" +
                "${getString(R.string.description_p2)} \n \n" +
                "\uD83D\uDCF1 ${getString(R.string.description_p3)} ${appVersion()}"

        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle(getString(R.string.app_information))
            setMessage(message)
            setPositiveButton(getString(R.string.quit)) { dialog, i ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun appVersion() : String {
        val appInfo = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
        val version = appInfo.versionName
        return version
    }

}