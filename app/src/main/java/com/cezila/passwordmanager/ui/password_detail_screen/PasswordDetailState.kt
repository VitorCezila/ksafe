package com.cezila.passwordmanager.ui.password_detail_screen

import com.cezila.passwordmanager.domain.model.Password

sealed class PasswordDetailState {

    data object Opened : PasswordDetailState()

    data object Loading : PasswordDetailState()

    data object ShowError : PasswordDetailState()

    data class ShowPasswordDetail(
        val id: Int?,
        val title: String,
        val decryptedPassword: String,
        val creationTimestamp: String,
        val login: String?,
        val url: String?
    ) : PasswordDetailState()

}