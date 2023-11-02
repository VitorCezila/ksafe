package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.repository.StorePasswordRepository

class GetPasswordsUseCase(
    private val storePasswordRepository: StorePasswordRepository,
) {

    suspend operator fun invoke(): List<Password?> {
        return storePasswordRepository.getPasswords()
    }

}