package com.cezila.ksafe.ui.create_password_screen

sealed class CreatePasswordState {
    data object Opened : CreatePasswordState()
    data object Loading : CreatePasswordState()
    data object TitleEmptyError : CreatePasswordState()
    data object PasswordEmptyError : CreatePasswordState()
    data object CreatePasswordSuccess : CreatePasswordState()
}
