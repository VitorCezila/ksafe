package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.core.utils.Resource
import com.cezila.ksafe.data.repository.FakeStorePasswordRepositoryImpl
import com.cezila.ksafe.domain.model.Errors
import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.repository.StorePasswordRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdatePasswordUseCaseTest {

    private lateinit var repository: StorePasswordRepository
    private lateinit var updatePasswordUseCase: UpdatePasswordUseCase

    @Before
    fun setup() {
        repository = FakeStorePasswordRepositoryImpl()
        updatePasswordUseCase = UpdatePasswordUseCase(repository)
    }

    @Test
    fun `updatePassword should update the password successfully`() = runTest {
        val originalPassword = Password(
            id = 1,
            title = "Example",
            login = "user@example.com",
            url = "https://example.com",
            encryptedPassword = "original_encrypted_password"
        )

        repository.insertPassword(originalPassword)

        val updatedPassword = originalPassword.copy(
            title = "Updated Example",
            login = "updated_user@example.com",
            url = "https://updated-example.com",
            encryptedPassword = "updated_encrypted_password"
        )

        val result = updatePasswordUseCase(updatedPassword)

        assertTrue(result.updateResult is Resource.Success)
        assertNull(result.passwordError)
        assertNull(result.titleError)

        val retrievedPassword = repository.getPasswordById(1)
        assertEquals(updatedPassword, retrievedPassword)
    }

    @Test
    fun `updatePassword should do nothing for non-existent password`() = runTest {
        val updatedPassword = Password(
            id = 1,
            title = "Updated Example",
            login = "updated_user@example.com",
            url = "https://updated-example.com",
            encryptedPassword = "updated_encrypted_password"
        )

        val result = updatePasswordUseCase(updatedPassword)

        assertTrue(result.updateResult is Resource.Success)
        assertNull(result.passwordError)
        assertNull(result.titleError)

        val passwords = repository.getPasswords()
        assertEquals(0, passwords.size)
    }

    @Test
    fun `updatePassword with empty title should return FieldEmpty error`() = runTest {
        val originalPassword = Password(
            id = 1,
            title = "Example",
            login = "user@example.com",
            url = "https://example.com",
            encryptedPassword = "original_encrypted_password"
        )

        repository.insertPassword(originalPassword)

        val updatedPassword = originalPassword.copy(
            title = "",
            login = "updated_user@example.com",
            url = "https://updated-example.com",
            encryptedPassword = "updated_encrypted_password"
        )

        val result = updatePasswordUseCase(updatedPassword)

        assertTrue(result.titleError is Errors.FieldEmpty)
        assertNull(result.updateResult)
        assertNull(result.passwordError)
        val retrievedPassword = repository.getPasswordById(1)
        assertNotEquals(updatedPassword, retrievedPassword)
    }

    @Test
    fun `updatePassword with empty password should return FieldEmpty error`() = runTest {
        val originalPassword = Password(
            id = 1,
            title = "Example",
            login = "user@example.com",
            url = "https://example.com",
            encryptedPassword = "original_encrypted_password"
        )

        repository.insertPassword(originalPassword)

        val updatedPassword = originalPassword.copy(
            title = "Updated Example",
            login = "updated_user@example.com",
            url = "https://updated-example.com",
            encryptedPassword = ""
        )

        val result = updatePasswordUseCase(updatedPassword)

        assertTrue(result.passwordError is Errors.FieldEmpty)
        assertNull(result.updateResult)
        assertNull(result.titleError)
        val retrievedPassword = repository.getPasswordById(1)
        assertNotEquals(updatedPassword, retrievedPassword)
    }
}