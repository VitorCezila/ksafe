package com.cezila.ksafe.ui.generate_password_screen

sealed class GeneratePasswordState {
    data object Opened : GeneratePasswordState()
    data object Loading : GeneratePasswordState()
    data object Error : GeneratePasswordState()
    data class ShowGeneratedPassword(val password: String) : GeneratePasswordState()
}
