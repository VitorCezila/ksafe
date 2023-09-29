package com.cezila.passwordmanager.di

import com.cezila.passwordmanager.core.encryption.EncryptionService
import com.cezila.passwordmanager.core.encryption.KeyManager
import com.cezila.passwordmanager.data.keystore.AesEncryptionServiceImpl
import com.cezila.passwordmanager.domain.use_case.DecryptPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EncryptionModule {

    @Provides
    @Singleton
    fun provideEncryptionService(
        keyManager: KeyManager
    ): EncryptionService {
        return AesEncryptionServiceImpl(
            secretKey = keyManager.getSecretKey("masterkey_alias")
        )
    }

    @Provides
    @Singleton
    fun provideDecryptPasswordUseCase(
        encryptionService: EncryptionService
    ): DecryptPasswordUseCase {
        return DecryptPasswordUseCase(encryptionService)
    }
}