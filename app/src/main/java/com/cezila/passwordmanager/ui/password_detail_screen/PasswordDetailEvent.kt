package com.cezila.passwordmanager.ui.password_detail_screen

sealed class PasswordDetailEvent {
    data class GetPasswordDetailById(val passwordId: Int) : PasswordDetailEvent()
    data class DeletePassword(val id: Int?) : PasswordDetailEvent()
}
