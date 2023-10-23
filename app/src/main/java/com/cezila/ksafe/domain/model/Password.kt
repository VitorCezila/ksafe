package com.cezila.ksafe.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.io.Serializable

data class Password(
    val id: Int? = null,
    val title: String,
    val login: String? = null,
    val url: String? = null,
    val encryptedPassword: String,
    val creationTimestamp: Instant = Clock.System.now()
) : Serializable {
    fun getFormattedCreationTimeStamp(): String {
        val localDateTime = creationTimestamp.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.monthNumber}/${localDateTime.dayOfMonth}/${localDateTime.year}"
    }
}