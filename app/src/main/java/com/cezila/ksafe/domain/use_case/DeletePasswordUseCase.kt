package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.domain.repository.StorePasswordRepository

class DeletePasswordUseCase(
    private val storePasswordRepository: StorePasswordRepository,
) {
    suspend operator fun invoke(id: Int?) {
        storePasswordRepository.deletePassword(id)
    }
}