package com.cezila.ksafe.ui.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cezila.ksafe.core.utils.Constants.SEARCH_NEWS_TIME_DELAY
import com.cezila.ksafe.core.utils.Resource
import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.use_case.DecryptPasswordUseCase
import com.cezila.ksafe.domain.use_case.PasswordUseCases
import com.cezila.ksafe.ui.create_password_screen.CreatePasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val passwordUseCases: PasswordUseCases,
    private val decryptPasswordUseCase: DecryptPasswordUseCase
) : ViewModel() {

    private val _state = MutableLiveData<HomeState>(HomeState.Opened)
    val state: LiveData<HomeState> = _state

    private var job: Job? = null

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnCopyClicked -> {
                decryptPassword(event.encryptedPassword)

            }
            is HomeEvent.OnSearch -> {
                fetchPasswordByQuery(event.query)
            }
            is HomeEvent.GetAllPasswords -> {
                _state.value = HomeState.Loading
                viewModelScope.launch {
                    _state.value =
                        HomeState.FetchPasswordResult(passwordUseCases.getPasswordsUseCase())
                }
            }
            is HomeEvent.SwipedToDelete -> {
                deletePassword(event.password)
            }
            is HomeEvent.UndoPasswordDelete -> {
                savePassword(event.password)
            }
            else -> {}
        }
    }

    private fun savePassword(password: Password) = viewModelScope.launch {
        _state.value = HomeState.Loading
        val insertPasswordResult = passwordUseCases.insertPasswordUseCase(password)
        if (insertPasswordResult.titleError != null || insertPasswordResult.passwordError != null) {
            _state.value = HomeState.UndoError
        }
        when (insertPasswordResult.insertResult) {
            is Resource.Success -> _state.value = HomeState.FetchPasswordResult(passwordUseCases.getPasswordsUseCase())
            is Resource.Loading -> _state.value = HomeState.Loading
            is Resource.Error -> _state.value = HomeState.UndoError
            else -> _state.value = HomeState.UndoError
        }
    }

    private fun decryptPassword(encryptedPassword: String) {
        _state.value = HomeState.CopiedPassword(decryptPasswordUseCase(encryptedPassword))
        _state.value = HomeState.CopiedPassword("")
    }

    private fun fetchPasswordByQuery(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(SEARCH_NEWS_TIME_DELAY)
            if (query.isNotEmpty()) {
                _state.value =
                    HomeState.FetchPasswordResult(passwordUseCases.searchPasswordUseCase(query))
            } else
                _state.value = HomeState.FetchPasswordResult(passwordUseCases.getPasswordsUseCase())
        }
    }

    private fun deletePassword(password: Password) = viewModelScope.launch {
        passwordUseCases.deletePasswordUseCase(password.id)
        val isPasswordDeleted = passwordUseCases.getPasswordByIdUseCase(password.id ?: -1) == null
        if (isPasswordDeleted) {
            _state.value = HomeState.PasswordDeletedSuccessfully(password)
            _state.value = HomeState.FetchPasswordResult(passwordUseCases.getPasswordsUseCase())
        }
    }

}