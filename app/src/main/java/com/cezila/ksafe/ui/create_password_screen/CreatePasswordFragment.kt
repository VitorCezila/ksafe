package com.cezila.ksafe.ui.create_password_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cezila.ksafe.R
import com.cezila.ksafe.core.utils.Resource
import com.cezila.ksafe.databinding.FragmentCreatePasswordBinding
import com.cezila.ksafe.ui.utils.navTo
import com.cezila.ksafe.ui.utils.toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePasswordFragment : Fragment(R.layout.fragment_create_password) {

    private val viewModel: CreatePasswordViewModel by viewModels()
    private lateinit var binding: FragmentCreatePasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreatePasswordBinding.bind(view)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).visibility =
            View.GONE
        addListeners()
    }

    private fun addListeners() {
        binding.btnSave.setOnClickListener {
            viewModel.onEvent(
                CreatePasswordEvent.OnCreateClicked(
                    title = binding.etName.text.toString(),
                    password = binding.etPassword.text.toString(),
                    login = binding.etLogin.text.toString(),
                    url = binding.etUrl.text.toString()
                )
            )
        }
        binding.btnBack.setOnClickListener {
            navTo(R.id.action_createPasswordFragment_to_homeFragment)
        }
        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    clearEditTexts()
                    toast("Password Created")
                }

                is Resource.Error -> {
                    toast(result.message)
                }

                is Resource.Loading -> {}
            }
        }
    }

    private fun clearEditTexts() {
        binding.etName.text?.clear()
        binding.etPassword.text?.clear()
        binding.etLogin.text?.clear()
        binding.etUrl.text?.clear()
    }

}