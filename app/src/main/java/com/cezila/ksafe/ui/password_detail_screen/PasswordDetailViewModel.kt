package com.cezila.ksafe.ui.password_detail_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cezila.ksafe.domain.use_case.DecryptPasswordUseCase
import com.cezila.ksafe.domain.use_case.PasswordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordDetailViewModel @Inject constructor(
    private val passwordUseCases: PasswordUseCases,
    private val decryptPasswordUseCase: DecryptPasswordUseCase
) : ViewModel() {

    private val _state = MutableLiveData<PasswordDetailState>(PasswordDetailState.Opened)
    val state: LiveData<PasswordDetailState> = _state

    fun onEvent(event: PasswordDetailEvent) {
        when (event) {
            is PasswordDetailEvent.GetPasswordDetailById -> fetchPasswordById(event.passwordId)

            is PasswordDetailEvent.DeletePassword -> deletePasswordById(event.id)
        }
    }

    private fun fetchPasswordById(id: Int) {
        _state.value = PasswordDetailState.Loading
        viewModelScope.launch {
            val password = passwordUseCases.getPasswordByIdUseCase(id)
            if (password != null) {
                val decryptedPassword = decryptPasswordUseCase(password.encryptedPassword)
                _state.value =
                    PasswordDetailState.ShowPasswordDetail(
                        id = password.id,
                        title = password.title,
                        decryptedPassword = decryptedPassword,
                        creationTimestamp = password.getFormattedCreationTimeStamp(),
                        login = password.login,
                        url = password.url
                    )
            } else {
                _state.value = PasswordDetailState.ShowError
            }
        }
    }

    private fun deletePasswordById(id: Int?) = viewModelScope.launch {
        passwordUseCases.deletePasswordUseCase(id)
    }

}