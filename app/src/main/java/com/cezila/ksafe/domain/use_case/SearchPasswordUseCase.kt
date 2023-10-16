package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.repository.StorePasswordRepository

class SearchPasswordUseCase(
    private val storePasswordRepository: StorePasswordRepository
) {
    suspend operator fun invoke(query: String): List<Password?> {
        return storePasswordRepository.searchPassword(query)
    }
}