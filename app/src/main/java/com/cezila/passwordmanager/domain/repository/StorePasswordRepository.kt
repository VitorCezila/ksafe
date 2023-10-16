package com.cezila.passwordmanager.domain.repository

import com.cezila.passwordmanager.core.utils.SimpleResource
import com.cezila.passwordmanager.domain.model.Password

interface StorePasswordRepository {

    suspend fun insertPassword(password: Password): SimpleResource

    suspend fun getPasswords(): List<Password?>

    suspend fun getPasswordById(id: Int): Password?

    suspend fun searchPassword(query: String): List<Password?>

    suspend fun updatePassword(password: Password): SimpleResource

    suspend fun deletePassword(id: Int?)

}