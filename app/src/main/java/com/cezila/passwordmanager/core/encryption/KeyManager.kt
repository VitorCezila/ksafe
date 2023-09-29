package com.cezila.passwordmanager.core.encryption

import javax.crypto.SecretKey

fun interface KeyManager {

    fun getSecretKey(alias: String): SecretKey

}