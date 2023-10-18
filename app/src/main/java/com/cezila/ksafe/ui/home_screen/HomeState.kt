package com.cezila.ksafe.ui.home_screen

import com.cezila.ksafe.domain.model.Password

sealed class HomeState {
    data object Opened : HomeState()
    data object Loading : HomeState()
    data class FetchPasswordResult(val passwords: List<Password?> = emptyList()) : HomeState()
    data class CopiedPassword(val decryptedPassword: String) : HomeState()
}
