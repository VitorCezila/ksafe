package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.core.encryption.EncryptionService
import com.cezila.ksafe.domain.model.InsertPasswordResult
import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.repository.StorePasswordRepository
import com.cezila.ksafe.domain.util.ValidationUtil

class InsertPasswordUseCase(
    private val storePasswordRepository: StorePasswordRepository,
    private val encryptionService: EncryptionService
) {

    suspend operator fun invoke(
        title: String,
        password: String,
        login: String? = null,
        url: String? = null
    ): InsertPasswordResult {
        val titleError = ValidationUtil.basicValidation(title)
        val passwordError = ValidationUtil.basicValidation(password)

        if (titleError != null || passwordError != null) {
            return InsertPasswordResult(
                titleError = titleError,
                passwordError = passwordError
            )
        }

        val passwordObj = Password(
            title = title,
            login = login,
            url = url,
            encryptedPassword = encryptionService.encrypt(password)
        )
        val result = storePasswordRepository.insertPassword(passwordObj)
        return InsertPasswordResult(insertResult = result)
    }

}