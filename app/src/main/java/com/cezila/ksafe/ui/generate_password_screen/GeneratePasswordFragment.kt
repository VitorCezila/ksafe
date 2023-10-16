package com.cezila.ksafe.ui.generate_password_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cezila.ksafe.R
import com.cezila.ksafe.core.utils.copyToClipboard
import com.cezila.ksafe.databinding.FragmentGeneratePasswordBinding
import com.cezila.ksafe.ui.utils.enable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneratePasswordFragment : Fragment(R.layout.fragment_generate_password) {

    private lateinit var binding: FragmentGeneratePasswordBinding
    private val viewModel: GeneratePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneratePasswordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        binding.backButton.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GeneratePasswordState.Opened -> renderOpenedState()
                is GeneratePasswordState.Loading -> renderLoadingState()
                is GeneratePasswordState.Error -> renderErrorState()
                is GeneratePasswordState.ShowGeneratedPassword -> renderShowGeneratedPassword(state.password)
            }
        }
    }

    private fun renderErrorState() {
        with(binding) {
            hideViews()
            tvGenerateError.enable(true)
        }
    }

    private fun renderShowGeneratedPassword(password: String) {
        with(binding) {
            showViews()
            tvGenerateError.enable(false)
            tvPasswordGenerated.text = password
            btnCopy.setOnClickListener {
                copyToClipboard(password, requireContext())
            }
            btnRandomize.setOnClickListener {
                generatePassword()
            }
        }
    }

    private fun renderLoadingState() {
        with(binding) {
            tvPasswordGenerated.text = ""
        }
    }

    private fun renderOpenedState() {
        with(binding) {
            tvGenerateError.enable(false)
            tvPasswordGenerated.text = ""
            generatePassword()
        }
    }

    private fun generatePassword() {
        viewModel.onEvent(
            GeneratePasswordEvent.GenerateRandomPassword(
                length = binding.sliderPasswordLength.value.toInt(),
                includeUppercase = binding.switchUppercase.isChecked,
                includeSymbols = binding.switchSymbol.isChecked
            )
        )
    }

    private fun showViews() {
        with(binding) {
            tvGenerateError.enable(true)
            backButton.enable(true)
            tvGenerateNew.enable(true)
            tvPasswordGenerated.enable(true)
            tvNameHint.enable(true)
            sliderPasswordLength.enable(true)
            llSwitchButtons.enable(true)
            llButtons.enable(true)
        }
    }

    private fun hideViews() {
        with(binding) {
            tvGenerateError.enable(false)
            backButton.enable(false)
            tvGenerateNew.enable(false)
            tvPasswordGenerated.enable(false)
            tvNameHint.enable(false)
            sliderPasswordLength.enable(false)
            llSwitchButtons.enable(false)
            llButtons.enable(false)
        }
    }
}