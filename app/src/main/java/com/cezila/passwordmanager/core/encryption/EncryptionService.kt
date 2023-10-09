package com.cezila.passwordmanager.core.encryption

interface EncryptionService {

    fun encrypt(data: String): String
    fun decrypt(encryptedData: String): String

}