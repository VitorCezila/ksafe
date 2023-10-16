package com.cezila.ksafe.core.encryption

interface EncryptionService {

    fun encrypt(data: String): String
    fun decrypt(encryptedData: String): String

}