package com.cezila.passwordmanager.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.io.Serializable

data class Password(
    val id: Int? = null,
    val title: String,
    val login: String? = null,
    val url: String? = null,
    val encryptedPassword: String,
    val creationTimestamp: Instant = Clock.System.now()
) : Serializable