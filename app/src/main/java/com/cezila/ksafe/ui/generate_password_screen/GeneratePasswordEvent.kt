package com.cezila.ksafe.ui.generate_password_screen

sealed class GeneratePasswordEvent {

    data class GenerateRandomPassword(
        val length: Int,
        val includeUppercase: Boolean,
        val includeSymbols: Boolean
    ) : GeneratePasswordEvent()

}
