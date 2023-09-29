package com.cezila.passwordmanager.data.repository

import com.cezila.passwordmanager.data.database.PasswordDao
import com.cezila.passwordmanager.data.mapper.toEntity
import com.cezila.passwordmanager.data.mapper.toPassword
import com.cezila.passwordmanager.domain.model.Password
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository

class StorePasswordRepositoryImpl(
    private val dao: PasswordDao
) : StorePasswordRepository {

    override suspend fun insertPassword(password: Password) {
        dao.insertPassword(password.toEntity())
    }

    override suspend fun getPasswords(): List<Password?> {
        return dao.getPasswords().map {
            it?.toPassword()
        }
    }

    override suspend fun getPasswordById(id: Int): Password? {
        TODO("Not yet implemented")
    }

    override suspend fun searchPassword(query: String): List<Password?> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePassword(password: Password) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePassword(id: Int) {
        TODO("Not yet implemented")
    }

}