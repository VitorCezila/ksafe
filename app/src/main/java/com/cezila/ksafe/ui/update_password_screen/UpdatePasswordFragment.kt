package com.cezila.ksafe.ui.update_password_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cezila.ksafe.R
import com.cezila.ksafe.databinding.FragmentUpdatePasswordBinding
import com.cezila.ksafe.ui.utils.ArgumentsId.TAG_PASSWORD_ID
import com.cezila.ksafe.ui.utils.enable
import com.cezila.ksafe.ui.utils.navTo
import com.cezila.ksafe.ui.utils.toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdatePasswordFragment : Fragment(R.layout.fragment_update_password) {

    private lateinit var binding: FragmentUpdatePasswordBinding
    private val viewModel: UpdatePasswordViewModel by viewModels()

    private val passwordId: Int by lazy {
        arguments?.getInt(TAG_PASSWORD_ID) ?: -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).visibility =
            View.GONE
        binding = FragmentUpdatePasswordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UpdatePasswordState.Opened -> renderOpenState()
                is UpdatePasswordState.Loading -> renderLoadingState()
                is UpdatePasswordState.ShowError -> renderShowErrorState(state.errorMessage)
                is UpdatePasswordState.UpdateSuccess -> renderUpdateSuccessState()
                is UpdatePasswordState.ShowingPasswordInfo -> renderShowPasswordInfoState(
                    title = state.title,
                    password = state.password,
                    login = state.login,
                    url = state.url
                )
            }
        }
    }

    private fun renderShowPasswordInfoState(
        title: String,
        password: String,
        login: String?,
        url: String?
    ) {
        with(binding) {
            showViews()
            etName.setText(title)
            etPassword.setText(password)

            if (!login.isNullOrEmpty()) {
                etEmailUsername.setText(login)
            }

            if (!url.isNullOrEmpty()) {
                etUrl.setText(url)
            }

            updateButton.setOnClickListener {
                viewModel.onEvent(
                    UpdatePasswordEvent.OnUpdatePasswordClicked(
                        id = passwordId,
                        title = etName.text.toString(),
                        password = etPassword.text.toString(),
                        login = etEmailUsername.text.toString(),
                        url = etUrl.text.toString()
                    )
                )
            }

            binding.backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }

            pbUpdate.enable(false)
            tvUpdateError.enable(false)
        }
    }

    private fun renderShowErrorState(message: String?) {
        toast(message)
    }

    private fun renderUpdateSuccessState() {
        toast("Password Updated Successfully")
        navTo(
            R.id.action_updatePasswordFragment_to_passwordDetailFragment,
            bundleOf(TAG_PASSWORD_ID to passwordId)
        )
    }

    private fun renderLoadingState() {
        hideViews()
        binding.pbUpdate.enable(true)
    }

    private fun renderOpenState() {
        if (passwordId < 0) {
            renderShowErrorState("Unknown Error")
        } else {
            viewModel.onEvent(UpdatePasswordEvent.GetPasswordInfo(passwordId))
        }
    }

    private fun showViews() {
        with(binding) {
            tvUpdateError.enable(true)
            pbUpdate.enable(true)
            backButton.enable(true)
            tvUpdatePasswordTitle.enable(true)
            tvNameHint.enable(true)
            etName.enable(true)
            tvUrlHint.enable(true)
            etUrl.enable(true)
            tvEmailUsernameHint.enable(true)
            etEmailUsername.enable(true)
            tvPasswordHint.enable(true)
            etPassword.enable(true)
            updateButton.enable(true)
        }
    }

    private fun hideViews() {
        with(binding) {
            tvUpdateError.enable(false)
            pbUpdate.enable(false)
            backButton.enable(false)
            tvUpdatePasswordTitle.enable(false)
            tvNameHint.enable(false)
            etName.enable(false)
            tvUrlHint.enable(false)
            etUrl.enable(false)
            tvEmailUsernameHint.enable(false)
            etEmailUsername.enable(false)
            tvPasswordHint.enable(false)
            etPassword.enable(false)
            updateButton.enable(false)
        }
    }

}