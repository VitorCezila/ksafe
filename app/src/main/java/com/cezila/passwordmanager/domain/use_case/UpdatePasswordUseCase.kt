package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.core.utils.Resource
import com.cezila.passwordmanager.core.utils.SimpleResource
import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository

class UpdatePasswordUseCase(
    private val storePasswordRepository: StorePasswordRepository
) {

    suspend operator fun invoke(password: Password): SimpleResource {
        if(password.title.isEmpty()) {
            return Resource.Error(
                message = "Title field cannot be empty"
            )
        }
        if(password.encryptedPassword.isEmpty()) {
            return Resource.Error(
                message = "Password field cannot be empty"
            )
        }

        return storePasswordRepository.updatePassword(password)
    }

}