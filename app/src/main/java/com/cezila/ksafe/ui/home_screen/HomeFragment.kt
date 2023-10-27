package com.cezila.ksafe.ui.home_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cezila.ksafe.R
import com.cezila.ksafe.core.utils.copyToClipboard
import com.cezila.ksafe.databinding.FragmentHomeBinding
import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.ui.utils.ArgumentsId.TAG_PASSWORD_ID
import com.cezila.ksafe.ui.utils.MarginItemDecoration
import com.cezila.ksafe.ui.utils.enable
import com.cezila.ksafe.ui.utils.navTo
import com.cezila.ksafe.ui.utils.showBottomNavView
import com.cezila.ksafe.ui.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val mAdapter = HomeAdapter(
        onItemClicked = ::onPasswordClicked,
        onCopyClicked = ::onCopyContentClicked
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        showBottomNavView(R.id.bottom_navigation_view)
        addOnBackPressedCallback()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeState()
    }

    private fun addOnBackPressedCallback() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    private fun initViews() {
        setupRecyclerView()
        setupFab()
        setupSearchEditText()
    }

    private fun setupRecyclerView() {
        with(binding.rvPasswords) {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.mergin)))
            setupSwipeToDelete()
        }
    }

    private fun setupFab() {
        binding.fabNewPassword.setOnClickListener {
            navTo(R.id.action_homeFragment_to_createPasswordFragment)
        }
    }

    private fun setupSearchEditText() {
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
                is HomeState.PasswordDeletedSuccessfully -> renderPasswordDeletedSuccessfully(state.password)
                is HomeState.UndoError -> renderUndoError()
            }
        }
    }

    private fun renderUndoError() {
        with(binding) {
            showViews()
            pbHome.enable(false)
            llNoResults.enable(false)
            showSnackbar(getString(R.string.msg_error_to_delete_password))
        }
    }

    private fun renderPasswordDeletedSuccessfully(password: Password) {
        showSnackbar(
            message = getString(R.string.msg_password_deleted_successfully),
            actionText = getString(R.string.undo)
        ) {
            undoPasswordDelete(password)
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
            }
            mAdapter.submitList(passwords)
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

    private fun setupSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                try {
                    val password =  mAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onEvent(HomeEvent.SwipedToDelete(password))
                } catch (e: IndexOutOfBoundsException) {
                    showSnackbar(getString(R.string.unknown_error))
                    viewModel.onEvent(HomeEvent.GetAllPasswords)
                }

            }

        }).attachToRecyclerView(binding.rvPasswords)
    }

    private fun undoPasswordDelete(password: Password) {
        viewModel.onEvent(HomeEvent.UndoPasswordDelete(password))
        binding.etSearch.setText("")
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