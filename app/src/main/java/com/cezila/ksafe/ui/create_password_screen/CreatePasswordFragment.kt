package com.cezila.ksafe.ui.create_password_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cezila.ksafe.R
import com.cezila.ksafe.databinding.FragmentCreatePasswordBinding
import com.cezila.ksafe.ui.utils.enable
import com.cezila.ksafe.ui.utils.hideBottomNavView
import com.cezila.ksafe.ui.utils.navTo
import com.cezila.ksafe.ui.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePasswordFragment : Fragment(R.layout.fragment_create_password) {

    private val viewModel: CreatePasswordViewModel by viewModels()
    private lateinit var binding: FragmentCreatePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePasswordBinding.inflate(inflater)
        hideBottomNavView(R.id.bottom_navigation_view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeState()
    }

    private fun setupUI() {
        with(binding) {
            btnSave.setOnClickListener { createPassword() }
            btnBack.setOnClickListener { navTo(R.id.action_createPasswordFragment_to_homeFragment) }
            etName.doOnTextChanged { _, _, _, _ -> tiName.error = null }
            etPassword.doOnTextChanged { _, _, _, _ -> tiPassword.error = null }
        }
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CreatePasswordState.Opened -> renderOpenedState()
                is CreatePasswordState.Loading -> renderLoadingState()
                is CreatePasswordState.TitleEmptyError -> renderTitleEmptyError()
                is CreatePasswordState.PasswordEmptyError -> renderPasswordEmptyError()
                is CreatePasswordState.CreatePasswordSuccess -> renderInsertPasswordSuccess()
            }
        }
    }

    private fun createPassword() {
        with(binding) {
            val title = etName.text.toString()
            val password = etPassword.text.toString()
            val login = etLogin.text.toString()
            val url = etUrl.text.toString()

            viewModel.onEvent(CreatePasswordEvent.OnCreateClicked(title, password, login, url))
        }
    }

    private fun renderOpenedState() {
        with(binding) {
            showViews()
            pbCreatePassword.enable(false)
        }
    }

    private fun renderLoadingState() {
        hideViews()
        binding.pbCreatePassword.enable(true)
    }

    private fun renderTitleEmptyError() {
        showViews()
        binding.pbCreatePassword.enable(false)
        binding.tiName.error = getString(R.string.msg_name_field_empty_error)
    }

    private fun renderPasswordEmptyError() {
        showViews()
        binding.pbCreatePassword.enable(false)
        binding.tiPassword.error = getString(R.string.msg_password_field_empty_error)
    }

    private fun renderInsertPasswordSuccess() {
        showViews()
        binding.pbCreatePassword.enable(false)
        clearEditTexts()
        view?.let {
            showSnackbar(getString(R.string.msg_password_created_successfully))
        }
    }

    private fun clearEditTexts() {
        binding.etName.text?.clear()
        binding.etPassword.text?.clear()
        binding.etLogin.text?.clear()
        binding.etUrl.text?.clear()
    }

    private fun showViews() {
        with(binding) {
            tiName.enable(true)
            tiPassword.enable(true)
            tiLogin.enable(true)
            tiUrl.enable(true)
            btnSave.enable(true)
            btnBack.enable(true)
            pbCreatePassword.enable(true)
        }
    }

    private fun hideViews() {
        with(binding) {
            tiName.enable(false)
            tiPassword.enable(false)
            tiLogin.enable(false)
            tiUrl.enable(false)
            btnSave.enable(false)
            btnBack.enable(false)
            pbCreatePassword.enable(false)
        }
    }
}