package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository

class SearchPasswordUseCase(
    private val storePasswordRepository: StorePasswordRepository
) {
    suspend operator fun invoke(query: String): List<Password?> {
        return storePasswordRepository.searchPassword(query)
    }
}