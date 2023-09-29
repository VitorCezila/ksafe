package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository

class UpdatePasswordUseCase(
    private val storePasswordRepository: StorePasswordRepository
) {

    suspend operator fun invoke(password: Password) {
        storePasswordRepository.updatePassword(password)
    }

}