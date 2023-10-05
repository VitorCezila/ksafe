package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.domain.data.repository.FakeStorePasswordRepositoryImpl
import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPasswordsUseCaseTest {

    private lateinit var repository: StorePasswordRepository
    private lateinit var getPasswordsUseCase: GetPasswordsUseCase

    @Before
    fun setup() {
        repository = FakeStorePasswordRepositoryImpl()
        getPasswordsUseCase = GetPasswordsUseCase(repository)
    }

    @Test
    fun `getPasswords should return empty list when no passwords are stored`() = runTest {
        val result = getPasswordsUseCase()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getPasswords should return list of passwords when passwords are stored`() = runTest {
        val password1 = Password(
            id = 1,
            title = "Example 1",
            login = "user1@example.com",
            url = "https://example.com/1",
            encryptedPassword = "encrypted_password_1"
        )
        val password2 = Password(
            id = 2,
            title = "Example 2",
            login = "user2@example.com",
            url = "https://example.com/2",
            encryptedPassword = "encrypted_password_2"
        )
        repository.insertPassword(password1)
        repository.insertPassword(password2)

        val result = getPasswordsUseCase()

        assertEquals(2, result.size)
        assertTrue(result.contains(password1))
        assertTrue(result.contains(password2))
    }

}