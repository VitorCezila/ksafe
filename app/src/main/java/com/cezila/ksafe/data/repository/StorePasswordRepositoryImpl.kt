package com.cezila.ksafe.data.repository

import com.cezila.ksafe.core.utils.Resource
import com.cezila.ksafe.core.utils.SimpleResource
import com.cezila.ksafe.data.database.PasswordDao
import com.cezila.ksafe.data.mapper.toEntity
import com.cezila.ksafe.data.mapper.toPassword
import com.cezila.ksafe.domain.model.Password
import com.cezila.ksafe.domain.repository.StorePasswordRepository

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

    override suspend fun updatePassword(password: Password): SimpleResource {
        dao.updatePassword(password.toEntity())
        return Resource.Success(Unit)
    }

    override suspend fun deletePassword(id: Int?) {
        dao.deletePassword(id)
    }

}