package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository

class GetPasswordByIdUseCase(
    private val storePasswordRepository: StorePasswordRepository,
) {
    suspend operator fun invoke(id: Int): Password? {
        return storePasswordRepository.getPasswordById(id)
    }
}