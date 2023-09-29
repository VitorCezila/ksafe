package com.cezila.passwordmanager.ui.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.use_case.DecryptPasswordUseCase
import com.cezila.passwordmanager.domain.use_case.PasswordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val passwordUseCases: PasswordUseCases,
    private val decryptPasswordUseCase: DecryptPasswordUseCase
) : ViewModel() {

    fun criarSenha() = viewModelScope.launch {
        passwordUseCases.insertPasswordUseCase(
            password = "Apenas1234"
        )
    }
    fun recuperarSenha() = viewModelScope.launch {
        passwordUseCases.getPasswordsUseCase().forEach {
            it?.let { password ->
                Log.d("ViewModel", "Login e Senha ${password.login} - ${password.encryptedPassword}")
                Log.d("ViewModel", "Senha decryptada: ${decryptPasswordUseCase(password.encryptedPassword)}")
            }
        }
    }

}