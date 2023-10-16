package com.cezila.ksafe.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_model")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val login: String? = null,
    val url: String? = null,
    val encryptedPassword: String,
    val creationTimestamp: Long
)
