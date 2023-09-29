package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository

class GetPasswordsUseCase(
    private val storePasswordRepository: StorePasswordRepository,
) {

    suspend operator fun invoke(): List<Password?> {
        return storePasswordRepository.getPasswords()
    }

}