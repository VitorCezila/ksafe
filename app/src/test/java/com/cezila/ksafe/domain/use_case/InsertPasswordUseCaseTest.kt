package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.core.encryption.EncryptionService
import com.cezila.ksafe.core.utils.Resource
import com.cezila.ksafe.data.keystore.FakeEncryptionServiceImpl
import com.cezila.ksafe.data.repository.FakeStorePasswordRepositoryImpl
import com.cezila.ksafe.domain.repository.StorePasswordRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InsertPasswordUseCaseTest {

    private lateinit var repository: StorePasswordRepository
    private lateinit var encryptionService: EncryptionService
    private lateinit var insertPasswordUseCase: InsertPasswordUseCase

    @Before
    fun setup() {
        repository = FakeStorePasswordRepositoryImpl()
        encryptionService = FakeEncryptionServiceImpl()
        insertPasswordUseCase = InsertPasswordUseCase(repository, encryptionService)
    }

    @Test
    fun `insertPassword with all correct values should return success`() = runTest {
        val result = runBlocking {
            insertPasswordUseCase.invoke(
                title = "Example",
                password = "Password123",
                login = "user@example.com",
                url = "https://example.com"
            )
        }

        assertTrue(result is Resource.Success)
        val passwords = repository.getPasswords()
        assertEquals(1, passwords.size)
    }

    @Test
    fun `insertPassword with empty title should return error`() = runTest {
        val result = runBlocking {
            insertPasswordUseCase.invoke(
                title = "",
                password = "Password123",
                login = "user@example.com",
                url = "https://example.com"
            )
        }

        assertTrue(result is Resource.Error)
        assertEquals("Title field cannot be empty", (result as Resource.Error).message)
        val passwords = repository.getPasswords()
        assertTrue(passwords.isEmpty())
    }

    @Test
    fun `insertPassword with empty password should return error`() = runTest {
        val result = runBlocking {
            insertPasswordUseCase.invoke(
                title = "Example",
                password = "",
                login = "user@example.com",
                url = "https://example.com"
            )
        }

        assertTrue(result is Resource.Error)
        assertEquals("Password field cannot be empty", (result as Resource.Error).message)
        val passwords = repository.getPasswords()
        assertTrue(passwords.isEmpty())
    }

    @Test
    fun `insertPassword with empty login should return success`() = runTest {
        val result = runBlocking {
            insertPasswordUseCase.invoke(
                title = "Example",
                password = "Password123",
                login = "",
                url = "https://example.com"
            )
        }

        assertTrue(result is Resource.Success)
        val passwords = repository.getPasswords()
        assertEquals(1, passwords.size)
    }

    @Test
    fun `insertPassword with empty URL should return success`() = runTest {
        val result = runBlocking {
            insertPasswordUseCase.invoke(
                title = "Example",
                password = "Password123",
                login = "user@example.com",
                url = ""
            )
        }

        assertTrue(result is Resource.Success)
        val passwords = repository.getPasswords()
        assertEquals(1, passwords.size)
    }
}