package com.cezila.ksafe.ui.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cezila.ksafe.core.utils.Constants.SEARCH_NEWS_TIME_DELAY
import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.use_case.DecryptPasswordUseCase
import com.cezila.ksafe.domain.use_case.PasswordUseCases
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

    private val _passwords: MutableLiveData<List<Password?>> = MutableLiveData(emptyList())
    val passwords: LiveData<List<Password?>> = _passwords

    private val _copiedPassowrd: MutableLiveData<String> = MutableLiveData("")
    val copiedPassword: LiveData<String> = _copiedPassowrd

    private var job: Job? = null

    init {
        viewModelScope.launch {
            _passwords.value = passwordUseCases.getPasswordsUseCase()
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnCopyClicked -> {
                _copiedPassowrd.value = decryptPasswordUseCase(event.encryptedPassword)
                _copiedPassowrd.value = ""
            }
            is HomeEvent.OnSearch -> {
                job?.cancel()
                job = viewModelScope.launch {
                    delay(SEARCH_NEWS_TIME_DELAY)
                    if (event.query.isNotEmpty()) {
                        _passwords.value = passwordUseCases.searchPasswordUseCase(event.query)
                    } else
                        _passwords.value = passwordUseCases.getPasswordsUseCase()
                }
            }
        }
    }
}