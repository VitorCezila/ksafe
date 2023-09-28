package com.cezila.passwordmanager.di

import android.app.Application
import androidx.room.Room
import com.cezila.passwordmanager.data.database.PasswordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePasswordDatabase(app: Application): PasswordDatabase {
        return Room.databaseBuilder(
            app,
            PasswordDatabase::class.java,
            PasswordDatabase.DATABASE_NAME
        ).build()
    }

}