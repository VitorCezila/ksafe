package com.cezila.ksafe.domain.use_case

data class PasswordUseCases(
    val insertPasswordUseCase: InsertPasswordUseCase,
    val deletePasswordUseCase: DeletePasswordUseCase,
    val getPasswordsUseCase: GetPasswordsUseCase,
    val getPasswordByIdUseCase: GetPasswordByIdUseCase,
    val searchPasswordUseCase: SearchPasswordUseCase,
    val updatePasswordUseCase: UpdatePasswordUseCase
)