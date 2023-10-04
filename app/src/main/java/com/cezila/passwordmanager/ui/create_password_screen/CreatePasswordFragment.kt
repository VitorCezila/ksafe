package com.cezila.passwordmanager.ui.create_password_screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cezila.passwordmanager.R
import com.cezila.passwordmanager.core.utils.Resource
import com.cezila.passwordmanager.databinding.FragmentCreatePasswordBinding
import com.cezila.passwordmanager.ui.utils.navTo
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
        binding.saveButton.setOnClickListener {
            viewModel.onEvent(
                CreatePasswordEvent.OnCreateClicked(
                    title = binding.etName.text.toString(),
                    password = binding.etPassword.text.toString(),
                    login = binding.etEmailUsername.text.toString(),
                    url = binding.etUrl.text.toString()
                )
            )
        }
        binding.backButton.setOnClickListener {
            navTo(R.id.action_createPasswordFragment_to_homeFragment)
        }
        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    clearEditTexts()
                    Toast.makeText(requireContext(), "Password Created", Toast.LENGTH_SHORT).show()
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {}
            }
        }
    }

    private fun clearEditTexts() {
        binding.etName.text.clear()
        binding.etPassword.text.clear()
        binding.etEmailUsername.text.clear()
        binding.etUrl.text.clear()
    }

}