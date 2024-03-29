package com.cezila.ksafe.data.mapper

import com.cezila.ksafe.data.database.PasswordEntity
import com.cezila.ksafe.domain.model.Password
import kotlinx.datetime.Instant

fun Password.toEntity(): PasswordEntity {
    return PasswordEntity(
        id = id,
        title = title,
        login = login,
        url = url,
        encryptedPassword = encryptedPassword,
        creationTimestamp = creationTimestamp.toEpochMilliseconds()
    )
}

fun PasswordEntity.toPassword(): Password {
    return Password(
        id = id,
        title = title,
        login = login,
        url = url,
        encryptedPassword = encryptedPassword,
        creationTimestamp = creationTimestamp.toInstant()
    )
}

private fun Long.toInstant() = Instant.fromEpochMilliseconds(this)