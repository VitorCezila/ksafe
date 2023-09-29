package com.cezila.passwordmanager.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PasswordEntity::class],
    version = 1
)
abstract class PasswordDatabase: RoomDatabase() {
    abstract val dao: PasswordDao
    companion object {
        const val DATABASE_NAME = "password.db"
    }
}