package com.cezila.passwordmanager.data.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.cezila.passwordmanager.core.encryption.KeyManager
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class KeyStoreKeyManagerImpl : KeyManager {

    private val keyStore: KeyStore = KeyStore.getInstance(KEYSTORE_PROVIDER)

    override fun getKey(alias: String): SecretKey {
        return loadKey(alias) ?: createrKey(alias)
    }

    private fun createrKey(alias: String): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_PROVIDER
        )
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }

    private fun loadKey(alias: String): SecretKey? {
        return keyStore.getKey(alias, null) as? SecretKey
    }

    companion object {
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val MASTER_KEY_ALIAS = "MasterKeyAlias"
    }
}