package com.cezila.ksafe.domain.use_case

import android.util.Log
import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.model.UpdatePasswordResult
import com.cezila.ksafe.domain.repository.StorePasswordRepository
import com.cezila.ksafe.domain.util.ValidationUtil

class UpdatePasswordUseCase(
    private val storePasswordRepository: StorePasswordRepository
) {

    suspend operator fun invoke(password: Password): UpdatePasswordResult {
        val titleError = ValidationUtil.basicValidation(password.title)
        val passwordError = ValidationUtil.basicValidation(password.encryptedPassword)

        if (titleError != null || passwordError != null) {
            return UpdatePasswordResult(
                titleError = titleError,
                passwordError = passwordError
            )
        }

        val updateResult = storePasswordRepository.updatePassword(password)
        return UpdatePasswordResult(updateResult = updateResult)
    }

}