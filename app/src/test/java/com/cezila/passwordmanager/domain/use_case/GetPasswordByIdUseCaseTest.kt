package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.data.repository.FakeStorePasswordRepositoryImpl
import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPasswordByIdUseCaseTest {

    private lateinit var repository: StorePasswordRepository
    private lateinit var getPasswordByIdUseCase: GetPasswordByIdUseCase

    @Before
    fun setup() {
        repository = FakeStorePasswordRepositoryImpl()
        getPasswordByIdUseCase = GetPasswordByIdUseCase(repository)
    }

    @Test
    fun `getPasswordById with valid id should return password`() = runTest {
        val password = Password(
            id = 1,
            title = "Example",
            login = "user@example.com",
            url = "https://example.com",
            encryptedPassword = "encrypted_password"
        )
        repository.insertPassword(password)

        val result = getPasswordByIdUseCase(1)

        assertNotNull(result)
        assertEquals(password, result)
    }

    @Test
    fun `getPasswordById with invalid id should return null`() = runTest {
        val password = Password(
            id = 1,
            title = "Example",
            login = "user@example.com",
            url = "https://example.com",
            encryptedPassword = "encrypted_password"
        )
        repository.insertPassword(password)

        val result = getPasswordByIdUseCase(2)
        assertNull(result)
    }

}