package com.cezila.passwordmanager.domain.use_case

data class PasswordUseCases(
    val insertPasswordUseCase: InsertPasswordUseCase,
    val deletePasswordUseCase: DeletePasswordUseCase,
    val getPasswordsUseCase: GetPasswordsUseCase
)