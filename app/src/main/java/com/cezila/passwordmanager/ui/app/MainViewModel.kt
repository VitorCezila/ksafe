package com.cezila.passwordmanager.ui.app

import androidx.lifecycle.ViewModel
import com.cezila.passwordmanager.domain.use_case.DecryptPasswordUseCase
import com.cezila.passwordmanager.domain.use_case.EncryptPasswordUseCase
import com.cezila.passwordmanager.domain.use_case.PasswordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val passwordUseCases: PasswordUseCases,
    private val decryptPasswordUseCase: DecryptPasswordUseCase,
    private val encryptPasswordUseCase: EncryptPasswordUseCase
) : ViewModel() {

}