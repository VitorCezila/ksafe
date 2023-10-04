package com.cezila.passwordmanager.data.repository

import com.cezila.passwordmanager.core.utils.Resource
import com.cezila.passwordmanager.core.utils.SimpleResource
import com.cezila.passwordmanager.data.database.PasswordDao
import com.cezila.passwordmanager.data.mapper.toEntity
import com.cezila.passwordmanager.data.mapper.toPassword
import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository

class StorePasswordRepositoryImpl(
    private val dao: PasswordDao
) : StorePasswordRepository {

    override suspend fun insertPassword(password: Password): SimpleResource {
        dao.insertPassword(password.toEntity())
        return Resource.Success(Unit)
    }

    override suspend fun getPasswords(): List<Password?> {
        return dao.getPasswords().map { it?.toPassword() }
    }

    override suspend fun getPasswordById(id: Int): Password? {
        return dao.getPasswordById(id)?.toPassword()
    }

    override suspend fun searchPassword(query: String): List<Password?> {
        return dao.searchPassword(query).map { it?.toPassword() }
    }

    override suspend fun updatePassword(password: Password) {
        dao.updatePassword(password.toEntity())
    }

    override suspend fun deletePassword(id: Int) {
        dao.deletePassword(id)
    }

}