package com.cezila.passwordmanager.core.encryption.implementation

import android.util.Base64
import com.cezila.passwordmanager.core.encryption.EncryptionService
import javax.crypto.Cipher
import javax.crypto.SecretKey

class AesEncryptionServiceImpl(private val secretKey: SecretKey): EncryptionService {

    override fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedValue = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedValue, Base64.DEFAULT)
    }

    override fun decrypt(encryptedData: String): String {
        val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedValue = cipher.doFinal(Base64.decode(encryptedData, Base64.DEFAULT))
        return String(decryptedValue)
    }

    companion object {
        private const val CIPHER_TRANSFORMATION = "AES/ECB/PKCS7Padding"
    }
}