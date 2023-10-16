package com.cezila.ksafe.ui.update_password_screen

sealed class UpdatePasswordState {

    data object Opened : UpdatePasswordState()
    data object Loading : UpdatePasswordState()
    data object UpdateSuccess : UpdatePasswordState()
    data class ShowError(val errorMessage: String? = null): UpdatePasswordState()

    data class ShowingPasswordInfo(
        val title: String,
        val password: String,
        val login: String?,
        val url: String?
    ) : UpdatePasswordState()

}
