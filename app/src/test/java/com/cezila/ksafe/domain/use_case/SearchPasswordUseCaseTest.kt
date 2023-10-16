package com.cezila.ksafe.domain.use_case

import com.cezila.ksafe.data.repository.FakeStorePasswordRepositoryImpl
import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.repository.StorePasswordRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchPasswordUseCaseTest {

    private lateinit var repository: StorePasswordRepository
    private lateinit var searchPasswordUserCase: SearchPasswordUseCase

    @Before
    fun setup() {
        repository = FakeStorePasswordRepositoryImpl()
        searchPasswordUserCase = SearchPasswordUseCase(repository)
    }

    @Test
    fun `searchPassword should return empty list when no matching passwords are found`() = runTest {
        val result = searchPasswordUserCase("example")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchPassword should return list of matching passwords when passwords are found`() = runTest {
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

            val result = searchPasswordUserCase("Example")

            assertEquals(2, result.size)
            assertTrue(result.contains(password1))
            assertTrue(result.contains(password2))
        }

    @Test
    fun `searchPassword should return list of matching passwords when partial matches are found`() = runTest {
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

            val result = searchPasswordUserCase("1")

            assertEquals(1, result.size)
            assertTrue(result.contains(password1))
            assertFalse(result.contains(password2))
        }
}