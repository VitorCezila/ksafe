package com.cezila.ksafe.ui.update_password_screen

sealed class UpdatePasswordState {

    data object Opened : UpdatePasswordState()
    data object Loading : UpdatePasswordState()
    data object UpdateSuccess : UpdatePasswordState()
    data object TitleEmptyError : UpdatePasswordState()
    data object PasswordEmptyError : UpdatePasswordState()
    data object UnknownError: UpdatePasswordState()

    data class ShowingPasswordInfo(
        val title: String,
        val password: String,
        val login: String?,
        val url: String?
    ) : UpdatePasswordState()

}
