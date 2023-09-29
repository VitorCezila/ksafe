package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.domain.model.PasswordGenerationParams
import com.cezila.passwordmanager.core.utils.PasswordGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeneratePasswordUseCase {

    suspend operator fun invoke(params: PasswordGenerationParams) =
        withContext(Dispatchers.Default) {
            PasswordGenerator(params).generatePassword()
        }

}