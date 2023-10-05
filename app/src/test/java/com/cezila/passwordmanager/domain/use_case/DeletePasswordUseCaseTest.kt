package com.cezila.passwordmanager.domain.use_case

import com.cezila.passwordmanager.domain.data.repository.FakeStorePasswordRepositoryImpl
import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeletePasswordUseCaseTest {

    private lateinit var repository: StorePasswordRepository
    private lateinit var deletePasswordUseCase: DeletePasswordUseCase

    @Before
    fun setup() {
        repository = FakeStorePasswordRepositoryImpl()
        deletePasswordUseCase = DeletePasswordUseCase(repository)
    }

    @Test
    fun `deletePassword with valid id should remove password`() = runTest {
        val password = Password(
            id = 1,
            title = "Example",
            login = "user@example.com",
            url = "https://example.com",
            encryptedPassword = "encrypted_password"
        )
        repository.insertPassword(password)

        deletePasswordUseCase(1)

        val result = repository.getPasswordById(1)
        assertNull(result)
    }

    @Test
    fun `deletePassword with invalid id should do nothing`() = runTest {
        val password = Password(
            id = 1,
            title = "Example",
            login = "user@example.com",
            url = "https://example.com",
            encryptedPassword = "encrypted_password"
        )
        repository.insertPassword(password)

        deletePasswordUseCase(2)

        val result = repository.getPasswordById(1)
        assertNotNull(result)
        assertEquals(result, password)
    }
}