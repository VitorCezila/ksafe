package com.cezila.passwordmanager.ui.update_password_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cezila.passwordmanager.core.utils.Resource
import com.cezila.passwordmanager.core.utils.SimpleResource
import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.use_case.DecryptPasswordUseCase
import com.cezila.passwordmanager.domain.use_case.EncryptPasswordUseCase
import com.cezila.passwordmanager.domain.use_case.PasswordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val passwordUseCases: PasswordUseCases,
    private val encryptPasswordUseCase: EncryptPasswordUseCase,
    private val decryptedPasswordUseCase: DecryptPasswordUseCase
) : ViewModel() {

    private val _state = MutableLiveData<UpdatePasswordState>(UpdatePasswordState.Opened)
    val state: LiveData<UpdatePasswordState> = _state

    fun onEvent(event: UpdatePasswordEvent) {
        when (event) {
            is UpdatePasswordEvent.OnUpdatePasswordClicked -> updatePassword(
                event.id,
                event.title,
                event.password,
                event.login,
                event.url
            )

            is UpdatePasswordEvent.GetPasswordInfo -> fetchPasswordById(event.id)
        }
    }

    private fun fetchPasswordById(id: Int) = viewModelScope.launch {
        _state.value = UpdatePasswordState.Loading
        val password = passwordUseCases.getPasswordByIdUseCase(id)
        if (password != null) {
            _state.value = UpdatePasswordState.ShowingPasswordInfo(
                title = password.title,
                password = decryptedPasswordUseCase(password.encryptedPassword),
                login = password.login,
                url = password.url
            )
        } else {
            _state.value = UpdatePasswordState.ShowError("Password Not found")
        }
    }

    private fun updatePassword(
        id: Int,
        title: String,
        password: String,
        login: String?,
        url: String?
    ) = viewModelScope.launch {
        val updatedPassword = Password(
            id = id,
            title = title,
            encryptedPassword = encryptPasswordUseCase(password),
            login = login,
            url = url
        )
        val result = passwordUseCases.updatePasswordUseCase(updatedPassword)
        when(result) {
            is Resource.Success -> {
                _state.value = UpdatePasswordState.UpdateSuccess
            }
            is Resource.Error -> {
                _state.value = UpdatePasswordState.ShowError(result.message)
            }
            is Resource.Loading -> {
                _state.value = UpdatePasswordState.Loading
            }
        }
    }
}