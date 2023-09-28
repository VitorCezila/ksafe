package com.cezila.passwordmanager.core.encryption

import javax.crypto.SecretKey

fun interface KeyManager {

    fun getKey(alias: String): SecretKey

}