package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.core.encryption.EncryptionService

class DecryptPasswordUseCase(
    private val encryptionService: EncryptionService
) {
    operator fun invoke(encryptedPassword: String): String {
        return encryptionService.decrypt(encryptedPassword)
    }
}