package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.core.encryption.EncryptionService

class DecryptPasswordUseCase(
    private val encryptionService: EncryptionService
) {
    operator fun invoke(encryptedPassword: String): String {
        return encryptionService.decrypt(encryptedPassword)
    }
}