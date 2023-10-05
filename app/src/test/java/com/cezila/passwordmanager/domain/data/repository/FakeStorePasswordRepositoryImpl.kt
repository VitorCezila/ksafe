package com.cezila.passwordmanager.domain.data.repository

import com.cezila.passwordmanager.core.utils.Resource
import com.cezila.passwordmanager.core.utils.SimpleResource
import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository

class FakeStorePasswordRepositoryImpl : StorePasswordRepository {

    private val storedPasswords = mutableListOf<Password>()

    override suspend fun insertPassword(password: Password): SimpleResource {
        storedPasswords.add(password)
        return Resource.Success(Unit)
    }

    override suspend fun getPasswords(): List<Password?> {
        return storedPasswords
    }

    override suspend fun getPasswordById(id: Int): Password? {
        return storedPasswords.find { it.id == id }
    }

    override suspend fun searchPassword(query: String): List<Password?> {
        return storedPasswords.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.login?.contains(query, ignoreCase = true) == true ||
                    it.url?.contains(query, ignoreCase = true) == true
        }
    }

    override suspend fun updatePassword(password: Password) {
        val existingPassword = storedPasswords.find { it.id == password.id }
        if (existingPassword != null) {
            storedPasswords.remove(existingPassword)
            storedPasswords.add(password)
        }
    }

    override suspend fun deletePassword(id: Int) {
        storedPasswords.removeAll { it.id == id }
    }
}