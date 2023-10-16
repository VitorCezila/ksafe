package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.core.encryption.EncryptionService
import com.cezila.ksafe.core.utils.Resource
import com.cezila.ksafe.core.utils.SimpleResource
import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.repository.StorePasswordRepository

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