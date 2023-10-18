package com.cezila.ksafe.ui.create_password_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cezila.ksafe.core.utils.Resource
import com.cezila.ksafe.domain.use_case.PasswordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
    private val passwordUseCases: PasswordUseCases,
) : ViewModel() {

    private val _state = MutableLiveData<CreatePasswordState>(CreatePasswordState.Opened)
    val state: LiveData<CreatePasswordState> = _state

    fun onEvent(event: CreatePasswordEvent) {
        when (event) {
            is CreatePasswordEvent.OnCreateClicked -> {
                _state.value = CreatePasswordState.Loading
                viewModelScope.launch {
                    val insertPasswordResult = passwordUseCases.insertPasswordUseCase(
                        title = event.title,
                        password = event.password,
                        login = event.login,
                        url = event.url
                    )
                    if(insertPasswordResult.titleError != null) {
                        _state.value = CreatePasswordState.TitleEmptyError
                    }
                    if(insertPasswordResult.passwordError != null) {
                        _state.value = CreatePasswordState.PasswordEmptyError
                    }
                    when(insertPasswordResult.insertResult) {
                        is Resource.Success -> _state.value = CreatePasswordState.CreatePasswordSuccess
                        is Resource.Loading -> _state.value = CreatePasswordState.Loading
                        is Resource.Error -> {}
                        else -> {}
                    }
                }
            }
        }
    }

}