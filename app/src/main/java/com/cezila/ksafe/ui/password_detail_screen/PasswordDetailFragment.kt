package com.cezila.ksafe.ui.password_detail_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cezila.ksafe.R
import com.cezila.ksafe.core.utils.copyToClipboard
import com.cezila.ksafe.databinding.FragmentPasswordDetailBinding
import com.cezila.ksafe.ui.utils.ArgumentsId.TAG_PASSWORD_ID
import com.cezila.ksafe.ui.utils.enable
import com.cezila.ksafe.ui.utils.hideBottomNavView
import com.cezila.ksafe.ui.utils.navTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordDetailFragment : Fragment(R.layout.fragment_password_detail) {

    private val viewModel: PasswordDetailViewModel by viewModels()
    private lateinit var binding: FragmentPasswordDetailBinding

    private val passwordId: Int by lazy {
        arguments?.getInt(TAG_PASSWORD_ID) ?: -1
    }

    private var passwordVisibility: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordDetailBinding.inflate(inflater)
        hideBottomNavView(R.id.bottom_navigation_view)
        addOnBackPressedCallback()
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
                    navTo(R.id.action_passwordDetailFragment_to_homeFragment)
                }
            })
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) {
            renderStates(it)
        }
    }

    private fun renderStates(state: PasswordDetailState) {
        when (state) {
            is PasswordDetailState.Opened -> renderOpenedState()
            is PasswordDetailState.Loading -> renderLoadingState()
            is PasswordDetailState.ShowError -> renderShowErrorState()
            is PasswordDetailState.ShowPasswordDetail -> renderShowPasswordDetailState(
                id = state.id,
                title = state.title,
                decryptedPassword = state.decryptedPassword,
                creationTimestamp = state.creationTimestamp,
                login = state.login,
                url = state.url
            )
        }
    }

    private fun renderShowErrorState() {
        hideViews()
        binding.tvDetailError.text = getString(R.string.error_message_try_again_later)
        binding.tvDetailError.enable(true)
    }

    private fun renderOpenedState() {
        if (passwordId > -1) {
            viewModel.onEvent(PasswordDetailEvent.GetPasswordDetailById(passwordId))
        } else {
            renderShowErrorState()
        }
    }

    private fun renderLoadingState() {
        hideViews()
        binding.pbDetails.enable(true)
    }

    private fun renderShowPasswordDetailState(
        id: Int?,
        title: String,
        decryptedPassword: String,
        creationTimestamp: String,
        login: String?,
        url: String?
    ) {
        with(binding) {
            showViews()
            tvPasswordTitle.text = title
            tvPassword.text = getHiddenPassword(decryptedPassword)
            tvDate.text = creationTimestamp

            if (login.isNullOrEmpty()) {
                tvLogin.enable(false)
                ivLogin.enable(false)
            } else {
                tvLogin.text = login
            }

            if (url.isNullOrEmpty()) {
                tvUrl.enable(false)
                ivUrl.enable(false)
            } else {
                tvUrl.text = url
            }

            btnBack.setOnClickListener {
                backFragment()
            }

            ivCopy.setOnClickListener {
                copyToClipboard(decryptedPassword, requireContext())
            }

            ivVisibility.setOnClickListener {
                togglePasswordVisibility(decryptedPassword)
            }

            btnDelete.setOnClickListener {
                viewModel.onEvent(PasswordDetailEvent.DeletePassword(id))
                backFragment()
            }

            btnUpdateDetail.setOnClickListener {
                navTo(
                    R.id.action_passwordDetailFragment_to_updatePasswordFragment,
                    bundleOf(TAG_PASSWORD_ID to id)
                )
            }

            pbDetails.enable(false)
            tvDetailError.enable(false)
        }
    }

    private fun hideViews() {
        with(binding) {
            pbDetails.enable(false)
            tvDetailError.enable(false)
            tvPasswordTitle.enable(false)
            ivCalendar.enable(false)
            tvDate.enable(false)
            ivUrl.enable(false)
            tvUrl.enable(false)
            ivLogin.enable(false)
            tvLogin.enable(false)
            ivPasswordLock.enable(false)
            rlPassword.enable(false)
            llButtons.enable(false)
            btnBack.enable(false)
        }
    }

    private fun showViews() {
        with(binding) {
            pbDetails.enable(true)
            tvDetailError.enable(true)
            tvPasswordTitle.enable(true)
            ivCalendar.enable(true)
            tvDate.enable(true)
            ivUrl.enable(true)
            tvUrl.enable(true)
            ivLogin.enable(true)
            tvLogin.enable(true)
            ivPasswordLock.enable(true)
            rlPassword.enable(true)
            llButtons.enable(true)
            btnBack.enable(true)
        }
    }

    private fun backFragment() {
        navTo(R.id.action_passwordDetailFragment_to_homeFragment)
    }

    private fun togglePasswordVisibility(password: String) {
        with(binding) {
            passwordVisibility = !passwordVisibility
            if (passwordVisibility) {
                tvPassword.text = password
                ivVisibility.setImageResource(R.drawable.ic_visibility_off)
            } else {
                tvPassword.text = getHiddenPassword(password)
                ivVisibility.setImageResource(R.drawable.ic_visibility_on)
            }

        }
    }

    private fun getHiddenPassword(passwordValue: String): String {
        return "*".repeat(passwordValue.length)
    }
}