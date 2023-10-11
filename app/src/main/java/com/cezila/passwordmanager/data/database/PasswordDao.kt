package com.cezila.passwordmanager.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: PasswordEntity)

    @Query("SELECT * FROM password_model")
    suspend fun getPasswords(): List<PasswordEntity?>

    @Query("SELECT * from password_model WHERE id = :id")
    suspend fun getPasswordById(id: Int): PasswordEntity?

    @Query(
        """
        SELECT *
        FROM password_model
        WHERE
        LOWER(title) LIKE '%' || LOWER(:query) || '%' OR
        LOWER(login) LIKE '%' || LOWER(:query) || '%'
    """
    )
    suspend fun searchPassword(query: String): List<PasswordEntity?>

    @Update
    suspend fun updatePassword(password: PasswordEntity)

    @Query("DELETE from password_model WHERE id = :id")
    suspend fun deletePassword(id: Int?)
}