package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.domain.model.PasswordGenerationParams
import com.cezila.ksafe.core.utils.PasswordGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeneratePasswordUseCase {

    suspend operator fun invoke(params: PasswordGenerationParams) =
        withContext(Dispatchers.Default) {
            PasswordGenerator(params).generatePassword()
        }

}