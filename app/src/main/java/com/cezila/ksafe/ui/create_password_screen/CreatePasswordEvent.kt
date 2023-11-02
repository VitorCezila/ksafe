package com.cezila.ksafe.ui.create_password_screen

sealed class CreatePasswordEvent {

    data class OnCreateClicked(
        val title: String,
        val password: String,
        val login: String? = null,
        val url: String? = null
    ) : CreatePasswordEvent()

}
