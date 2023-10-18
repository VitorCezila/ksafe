package com.cezila.ksafe.ui.home_screen

sealed class HomeEvent {
    data class OnCopyClicked(val encryptedPassword: String) : HomeEvent()
    data class OnSearch(val query: String) : HomeEvent()
    data object GetAllPasswords : HomeEvent()
}
