package com.cezila.ksafe.core.utils

import com.cezila.ksafe.core.exception.PasswordCreationLengthException
import com.cezila.ksafe.domain.model.PasswordCharGroup
import com.cezila.ksafe.domain.model.PasswordGenerationParams
import com.cezila.ksafe.core.utils.Constants.MIN_LENGTH_PASSWORD_GENERATION

class PasswordGenerator(
    private val params: PasswordGenerationParams
) {

    init {
        require(params.length >= MIN_LENGTH_PASSWORD_GENERATION) {
            throw PasswordCreationLengthException()
        }
    }

    private val availableGroup =
        params.charGroups + PasswordCharGroup.LowercaseLetters + PasswordCharGroup.Digits
    private val availableChars = createAvailableChars()

    private fun createAvailableChars() =
        availableGroup
            .map { it.chars }
            .reduce { acc, chars -> acc + chars }

    private fun ensureAtLeastOneCharFromEachGroup(password: String): Boolean {
        val charGroupMatched = availableGroup.all { charGroup ->
            password.any { it in charGroup.chars }
        }
        return charGroupMatched
    }

    fun generatePassword(): String {
        var generatedPassword: String

        do {
            generatedPassword = buildString {
                repeat(params.length) {
                    append(availableChars.random())
                }
            }
        } while (!ensureAtLeastOneCharFromEachGroup(generatedPassword))

        return generatedPassword
    }
}