package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.core.exception.PasswordCreationLengthException
import com.cezila.passwordmanager.domain.model.PasswordCharGroup
import com.cezila.passwordmanager.domain.model.PasswordGenerationParams
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GeneratePasswordUseCaseTest {

    @Test
    fun `generate password with all parameters`() = runTest {
        val params = PasswordGenerationParams(
            length = 10,
            charGroups = listOf(
                PasswordCharGroup.UppercaseLetters,
                PasswordCharGroup.Special,
            )
        )

        val useCase = GeneratePasswordUseCase()
        val generatedPassword = useCase.invoke(params)

        assertEquals(params.length, generatedPassword.length)
        assertTrue(generatedPassword.any { it.isDigit() })
        assertTrue(generatedPassword.any { it.isLowerCase() })
        assertTrue(generatedPassword.any { it.isUpperCase() })
        assertTrue(generatedPassword.any { it in "!@#$%^&*()-_+=<>?" })
    }

    @Test
    fun `generate password with uppercase parameter`() = runTest {
        val params = PasswordGenerationParams(
            length = 10,
            charGroups = listOf(PasswordCharGroup.UppercaseLetters)
        )

        val useCase = GeneratePasswordUseCase()
        val generatedPassword = useCase.invoke(params)

        assertEquals(params.length, generatedPassword.length)
        assertTrue(generatedPassword.any { it.isDigit() })
        assertTrue(generatedPassword.any { it.isLowerCase() })
        assertTrue(generatedPassword.any { it.isUpperCase() })
    }

    @Test
    fun `generate password with special symbol parameter`() = runTest {
        val params = PasswordGenerationParams(
            length = 10,
            charGroups = listOf(PasswordCharGroup.Special)
        )

        val useCase = GeneratePasswordUseCase()
        val generatedPassword = useCase.invoke(params)

        assertEquals(params.length, generatedPassword.length)
        assertTrue(generatedPassword.any { it.isDigit() })
        assertTrue(generatedPassword.any { it.isLowerCase() })
        assertTrue(generatedPassword.any { it in "!@#$%^&*()-_+=<>?" })
    }

    @Test
    fun `generate password without specific parameters`() = runTest {
        val params = PasswordGenerationParams(
            length = 10,
            charGroups = emptyList()
        )

        val useCase = GeneratePasswordUseCase()
        val generatedPassword = useCase.invoke(params)

        assertEquals(params.length, generatedPassword.length)
        assertTrue(generatedPassword.any { it.isDigit() })
        assertTrue(generatedPassword.any { it.isLowerCase() })
    }

    @Test(expected = PasswordCreationLengthException::class)
    fun `generate password with invalid length`() = runTest {
        val params = PasswordGenerationParams(
            length = 5, charGroups = listOf(
                PasswordCharGroup.UppercaseLetters,
                PasswordCharGroup.Special,
            )
        )

        val useCase = GeneratePasswordUseCase()
        useCase(params)
    }

}