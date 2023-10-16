package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.core.utils.Resource
import com.cezila.ksafe.core.utils.SimpleResource
import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.repository.StorePasswordRepository

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