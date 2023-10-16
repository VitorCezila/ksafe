package com.cezila.ksafe.data.keystore

import android.util.Base64
import com.cezila.ksafe.core.encryption.EncryptionService
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class AesEncryptionServiceImpl(private val secretKey: SecretKey) : EncryptionService {

    override fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedValue = iv + cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedValue, Base64.DEFAULT)
    }

    override fun decrypt(encryptedData: String): String {
        val combined = Base64.decode(encryptedData, Base64.DEFAULT)
        val iv = ByteArray(IV_SIZE_BYTES)
        val encryptedValue = ByteArray(combined.size - IV_SIZE_BYTES)
        System.arraycopy(combined, 0, iv, 0, IV_SIZE_BYTES)
        System.arraycopy(combined, IV_SIZE_BYTES, encryptedValue, 0, combined.size - IV_SIZE_BYTES)
        val ivSpec = GCMParameterSpec(TAG_SIZE_BITS, iv)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
        val decryptedValue = cipher.doFinal(encryptedValue)
        return String(decryptedValue)
    }

    companion object {
        private const val AES_ALGORITHM = "AES"
        private const val AES_KEY_SIZE_BITS = 256
        private const val AES_MODE = "GCM"
        private const val AES_PADDING = "NoPadding"
        private const val TRANSFORMATION = "$AES_ALGORITHM/$AES_MODE/$AES_PADDING"
        private const val IV_SIZE_BYTES = 12
        private const val TAG_SIZE_BITS = 128
    }
}