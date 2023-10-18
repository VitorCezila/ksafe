package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.core.encryption.EncryptionService

class EncryptPasswordUseCase(
    private val encryptionService: EncryptionService
) {
    operator fun invoke(password: String): String {
        if(password.isEmpty()) {
            return ""
        }

        return encryptionService.encrypt(password)
    }
}