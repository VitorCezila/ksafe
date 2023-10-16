package com.cezila.passwordmanager.ui.generate_password_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cezila.passwordmanager.domain.model.PasswordCharGroup
import com.cezila.passwordmanager.domain.model.PasswordGenerationParams
import com.cezila.passwordmanager.domain.use_case.GeneratePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneratePasswordViewModel @Inject constructor(
    private val generatePasswordUseCase: GeneratePasswordUseCase
) : ViewModel() {

    private val _state = MutableLiveData<GeneratePasswordState>(GeneratePasswordState.Opened)
    val state: LiveData<GeneratePasswordState> = _state

    fun onEvent(event: GeneratePasswordEvent) {
        when (event) {
            is GeneratePasswordEvent.GenerateRandomPassword -> generatePassword(
                length = event.length,
                includeSymbols = event.includeSymbols,
                includeUppercase = event.includeUppercase
            )
        }
    }

    private fun generatePassword(
        length: Int,
        includeSymbols: Boolean,
        includeUppercase: Boolean
    ) = viewModelScope.launch {
        _state.value = GeneratePasswordState.Loading
        val charGroup = mutableListOf<PasswordCharGroup>()
        if (includeSymbols) {
            charGroup.add(PasswordCharGroup.Special)
        }
        if (includeUppercase) {
            charGroup.add(PasswordCharGroup.UppercaseLetters)
        }
        val password = generatePasswordUseCase(
            PasswordGenerationParams(
                length = length,
                charGroups = charGroup
            )
        )
        _state.value = GeneratePasswordState.ShowGeneratedPassword(password)
    }

}