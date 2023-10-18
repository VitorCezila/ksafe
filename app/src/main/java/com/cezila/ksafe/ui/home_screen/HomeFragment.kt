package com.cezila.ksafe.ui.home_screen

import android.os.Bundle
import android.util.Log
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
        observeState()
    }

    private fun initViews() {
        binding.rvPasswords.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.mergin)))
        }
        binding.fabNewPassword.setOnClickListener {
            navTo(R.id.action_homeFragment_to_createPasswordFragment)
        }
        binding.etSearch.addTextChangedListener {
            viewModel.onEvent(HomeEvent.OnSearch(it.toString()))
        }
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeState.Opened -> renderOpenedState()
                is HomeState.Loading -> renderLoadingState()
                is HomeState.FetchPasswordResult -> renderFetchPasswordResult(state.passwords)
                is HomeState.CopiedPassword -> renderCopiedPasswordState(state.decryptedPassword)
            }
        }
    }

    private fun renderOpenedState() {
        viewModel.onEvent(HomeEvent.GetAllPasswords)
    }

    private fun renderLoadingState() {
        with(binding) {
            hideViews()
            pbHome.enable(true)
        }
    }

    private fun renderFetchPasswordResult(passwords: List<Password?>) {
        with(binding) {
            showViews()
            pbHome.enable(false)
            if (passwords.isEmpty()) {
                rvPasswords.enable(false)
                llNoResults.enable(true)
            } else {
                rvPasswords.enable(true)
                llNoResults.enable(false)
                rvPasswords.adapter = HomeAdapter(
                    passwords = passwords,
                    onItemClicked = ::onPasswordClicked,
                    onCopyClicked = ::onCopyContentClicked
                )

            }
        }
    }

    private fun renderCopiedPasswordState(decryptedPassword: String) {
        if (decryptedPassword.isNotBlank()) {
            copyToClipboard(decryptedPassword, requireContext())
        }
    }

    private fun onPasswordClicked(password: Password) {
        navTo(
            R.id.action_homeFragment_to_passwordDetailFragment,
            bundleOf(TAG_PASSWORD_ID to password.id)
        )
    }

    private fun onCopyContentClicked(encryptedPassword: String) {
        viewModel.onEvent(HomeEvent.OnCopyClicked(encryptedPassword = encryptedPassword))
    }

    private fun showViews() {
        with(binding) {
            pbHome.enable(true)
            fabNewPassword.enable(true)
            etSearch.enable(true)
            rvPasswords.enable(true)
            llNoResults.enable(true)
        }
    }

    private fun hideViews() {
        with(binding) {
            pbHome.enable(false)
            fabNewPassword.enable(false)
            etSearch.enable(false)
            rvPasswords.enable(false)
            llNoResults.enable(false)
        }
    }
}