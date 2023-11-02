package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.repository.StorePasswordRepository

class GetPasswordByIdUseCase(
    private val storePasswordRepository: StorePasswordRepository,
) {
    suspend operator fun invoke(id: Int): Password? {
        return storePasswordRepository.getPasswordById(id)
    }
}