package com.cezila.passwordmanager.di

import com.cezila.passwordmanager.core.encryption.KeyManager
import com.cezila.passwordmanager.data.keystore.KeyStoreKeyManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KeyModule {

    @Provides
    @Singleton
    fun provideKeyManager(): KeyManager {
        return KeyStoreKeyManagerImpl()
    }

}