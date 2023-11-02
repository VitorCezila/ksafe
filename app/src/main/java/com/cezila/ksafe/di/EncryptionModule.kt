package com.cezila.ksafe.di

import com.cezila.ksafe.core.encryption.EncryptionService
import com.cezila.ksafe.core.encryption.KeyManager
import com.cezila.ksafe.data.keystore.AesEncryptionServiceImpl
import com.cezila.ksafe.domain.use_case.DecryptPasswordUseCase
import com.cezila.ksafe.domain.use_case.EncryptPasswordUseCase
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
    fun provideEncryptPasswordUseCase(
        encryptionService: EncryptionService
    ): EncryptPasswordUseCase {
        return EncryptPasswordUseCase(encryptionService)
    }

    @Provides
    @Singleton
    fun provideDecryptPasswordUseCase(
        encryptionService: EncryptionService
    ): DecryptPasswordUseCase {
        return DecryptPasswordUseCase(encryptionService)
    }
}