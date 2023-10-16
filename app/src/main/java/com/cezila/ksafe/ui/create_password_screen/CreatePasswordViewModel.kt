package com.cezila.ksafe.ui.create_password_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cezila.ksafe.core.utils.SimpleResource
import com.cezila.ksafe.domain.use_case.PasswordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
    private val passwordUseCases: PasswordUseCases,
) : ViewModel() {

    private val _result: MutableLiveData<SimpleResource> = MutableLiveData()
    val result: LiveData<SimpleResource> = _result

    fun onEvent(event: CreatePasswordEvent) {
        when (event) {
            is CreatePasswordEvent.OnCreateClicked -> {
                viewModelScope.launch {
                    _result.value = passwordUseCases.insertPasswordUseCase(
                        title = event.title,
                        password = event.password,
                        login = event.login,
                        url = event.url
                    )
                }
            }
        }
    }

}