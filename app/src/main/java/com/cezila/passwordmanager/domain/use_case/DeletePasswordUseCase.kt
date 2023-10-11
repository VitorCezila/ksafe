package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.domain.repository.StorePasswordRepository

class DeletePasswordUseCase(
    private val storePasswordRepository: StorePasswordRepository,
) {
    suspend operator fun invoke(id: Int?) {
        storePasswordRepository.deletePassword(id)
    }
}