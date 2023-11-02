package com.cezila.ksafe.ui.update_password_screen

sealed class UpdatePasswordEvent {

    data class OnUpdatePasswordClicked(
        val id: Int,
        val title: String,
        val password: String,
        val login: String?,
        val url: String?
    ) : UpdatePasswordEvent()

    data class GetPasswordInfo(val id: Int) : UpdatePasswordEvent()

}
