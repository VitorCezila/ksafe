package com.cezila.ksafe.di

import com.cezila.ksafe.core.encryption.KeyManager
import com.cezila.ksafe.data.keystore.KeyStoreKeyManagerImpl
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