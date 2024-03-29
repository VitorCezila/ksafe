package com.cezila.ksafe.ui.update_password_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cezila.ksafe.R
import com.cezila.ksafe.databinding.FragmentUpdatePasswordBinding
import com.cezila.ksafe.ui.utils.ArgumentsId.TAG_PASSWORD_ID
import com.cezila.ksafe.ui.utils.enable
import com.cezila.ksafe.ui.utils.hideBottomNavView
import com.cezila.ksafe.ui.utils.navTo
import com.cezila.ksafe.ui.utils.showSnackbar
import com.cezila.ksafe.ui.utils.toast
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
        binding = FragmentUpdatePasswordBinding.inflate(inflater)
        addOnBackPressedCallback()
        hideBottomNavView(R.id.bottom_navigation_view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    private fun addOnBackPressedCallback() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navTo(
                        R.id.action_updatePasswordFragment_to_passwordDetailFragment,
                        bundleOf(TAG_PASSWORD_ID to passwordId)
                    )
                }
            })
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UpdatePasswordState.Opened -> renderOpenState()
                is UpdatePasswordState.Loading -> renderLoadingState()
                is UpdatePasswordState.UnknownError -> renderUnknownErrorState()
                is UpdatePasswordState.UpdateSuccess -> renderUpdateSuccessState()
                is UpdatePasswordState.TitleEmptyError -> renderTitleEmptyError()
                is UpdatePasswordState.PasswordEmptyError -> renderPasswordEmptyErrorState()
                is UpdatePasswordState.ShowingPasswordInfo -> renderShowPasswordInfoState(
                    title = state.title,
                    password = state.password,
                    login = state.login,
                    url = state.url
                )
            }
        }
    }

    private fun renderPasswordEmptyErrorState() {
        binding.pbUpdate.enable(false)
        binding.tiPassword.error = getString(R.string.msg_password_field_empty_error)
    }

    private fun renderTitleEmptyError() {
        binding.pbUpdate.enable(false)
        binding.tiName.error = getString(R.string.msg_name_field_empty_error)
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

            etName.doOnTextChanged { _, _, _, _ ->
                tiName.error = null
            }
            etPassword.doOnTextChanged { _, _, _, _ ->
                tiPassword.error = null
            }

            if (!login.isNullOrEmpty()) {
                etLogin.setText(login)
            }

            if (!url.isNullOrEmpty()) {
                etUrl.setText(url)
            }

            btnUpdate.setOnClickListener {
                viewModel.onEvent(
                    UpdatePasswordEvent.OnUpdatePasswordClicked(
                        id = passwordId,
                        title = etName.text.toString(),
                        password = etPassword.text.toString(),
                        login = etLogin.text.toString(),
                        url = etUrl.text.toString()
                    )
                )
            }

            binding.btnBack.setOnClickListener {
                navTo(
                    R.id.action_updatePasswordFragment_to_passwordDetailFragment,
                    bundleOf(TAG_PASSWORD_ID to passwordId)
                )
            }

            pbUpdate.enable(false)
        }
    }

    private fun renderUnknownErrorState() {
        toast(getString(R.string.unknown_error))
    }

    private fun renderUpdateSuccessState() {
        showSnackbar(getString(R.string.msg_password_update_successfully))
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
            renderUnknownErrorState()
        } else {
            viewModel.onEvent(UpdatePasswordEvent.GetPasswordInfo(passwordId))
        }
    }

    private fun showViews() {
        with(binding) {
            pbUpdate.enable(true)
            btnBack.enable(true)
            tvUpdatePasswordTitle.enable(true)
            tiName.enable(true)
            tiUrl.enable(true)
            tiLogin.enable(true)
            tiPassword.enable(true)
            btnUpdate.enable(true)
        }
    }

    private fun hideViews() {
        with(binding) {
            pbUpdate.enable(false)
            btnBack.enable(false)
            tvUpdatePasswordTitle.enable(false)
            tiName.enable(false)
            tiUrl.enable(false)
            tiLogin.enable(false)
            tiPassword.enable(false)
            btnUpdate.enable(false)
        }
    }

}