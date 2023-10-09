package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.core.encryption.EncryptionService

class EncryptPasswordUseCase(
    private val encryptionService: EncryptionService
) {
    operator fun invoke(password: String): String {
        return encryptionService.encrypt(password)
    }
}