package com.cezila.ksafe.ui.home_screen

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cezila.ksafe.R
import com.cezila.ksafe.core.utils.copyToClipboard
import com.cezila.ksafe.databinding.FragmentHomeBinding
import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.ui.utils.ArgumentsId.TAG_PASSWORD_ID
import com.cezila.ksafe.ui.utils.MarginItemDecoration
import com.cezila.ksafe.ui.utils.enable
import com.cezila.ksafe.ui.utils.navTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initViews()
        addObservers()
    }

    private fun initViews() {
        binding.contentRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.mergin)))
        }
    }

    private fun addObservers() {
        viewModel.passwords.observe(viewLifecycleOwner) { passwords ->
            if (passwords.isEmpty()) {
                setupUiStates(
                    enableNotFoundContent = true,
                    enableContentList = false
                )
            } else {
                setupUiStates(
                    enableNotFoundContent = false,
                    enableContentList = true
                )
                binding.contentRecyclerView.adapter = HomeAdapter(
                    passwords = passwords,
                    onItemClicked = ::onPasswordClicked,
                    onCopyClicked = ::onCopyContentClicked
                )
            }
        }
        viewModel.copiedPassword.observe(viewLifecycleOwner) { copiedPassword ->
            if (copiedPassword.isNotBlank()) {
                copyToClipboard(copiedPassword, requireContext())
            }
        }
        binding.fabNewPassword.setOnClickListener {
            navTo(R.id.action_homeFragment_to_createPasswordFragment)
        }
        binding.searchEditText.addTextChangedListener {
            viewModel.onEvent(HomeEvent.OnSearch(it.toString()))
        }
    }

    private fun onPasswordClicked(password: Password) {
        navTo(R.id.action_homeFragment_to_passwordDetailFragment, bundleOf(TAG_PASSWORD_ID to password.id))
    }

    private fun onCopyContentClicked(encryptedPassword: String) {
        viewModel.onEvent(HomeEvent.OnCopyClicked(encryptedPassword = encryptedPassword))
    }

    private fun setupUiStates(
        enableNotFoundContent: Boolean,
        enableContentList: Boolean
    ) {
        binding.contentRecyclerView.enable(enableContentList)
        binding.noResultsLayout.enable(enableNotFoundContent)
    }
}