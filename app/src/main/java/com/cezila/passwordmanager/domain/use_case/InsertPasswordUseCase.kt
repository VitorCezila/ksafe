package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.core.encryption.EncryptionService
import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository

class InsertPasswordUseCase(
    private val storePasswordRepository: StorePasswordRepository,
    private val encryptionService: EncryptionService
) {

    suspend operator fun invoke(
        title: String? = null,
        login: String? = null,
        password: String
    ) {
        val passwordObj = Password(
            title = title,
            login = login,
            encryptedPassword = encryptionService.encrypt(password)
        )
        storePasswordRepository.insertPassword(passwordObj)
    }

}