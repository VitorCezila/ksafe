package com.cezila.ksafe.data.keystore

import com.cezila.ksafe.core.encryption.EncryptionService

class FakeEncryptionServiceImpl : EncryptionService {

    override fun encrypt(data: String): String {
        return data
    }

    override fun decrypt(encryptedData: String): String {
        return encryptedData
    }
}