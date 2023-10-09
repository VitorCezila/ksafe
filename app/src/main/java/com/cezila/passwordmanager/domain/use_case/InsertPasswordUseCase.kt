package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.core.encryption.EncryptionService
import com.cezila.passwordmanager.core.utils.Resource
import com.cezila.passwordmanager.core.utils.SimpleResource
import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository

class InsertPasswordUseCase(
    private val storePasswordRepository: StorePasswordRepository,
    private val encryptionService: EncryptionService
) {

    suspend operator fun invoke(
        title: String,
        password: String,
        login: String? = null,
        url: String? = null
    ): SimpleResource {
        if(title.isEmpty()) {
            return Resource.Error(
                message = "Title field cannot be empty"
            )
        }
        if(password.isEmpty()) {
            return Resource.Error(
                message = "Password field cannot be empty"
            )
        }
        val passwordObj = Password(
            title = title,
            login = login,
            url = url,
            encryptedPassword = encryptionService.encrypt(password)
        )
        return storePasswordRepository.insertPassword(passwordObj)
    }

}