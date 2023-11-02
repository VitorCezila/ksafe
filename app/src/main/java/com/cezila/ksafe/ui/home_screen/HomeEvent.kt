package com.cezila.ksafe.ui.home_screen

import com.cezila.ksafe.domain.model.Password

sealed class HomeEvent {
    data class OnCopyClicked(val encryptedPassword: String) : HomeEvent()
    data class OnSearch(val query: String) : HomeEvent()
    data class SwipedToDelete(val password: Password) : HomeEvent()
    data class UndoPasswordDelete(val password: Password) : HomeEvent()
    data object GetAllPasswords : HomeEvent()
}
