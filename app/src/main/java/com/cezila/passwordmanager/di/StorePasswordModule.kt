package com.cezila.passwordmanager.di

import com.cezila.passwordmanager.core.encryption.EncryptionService
import com.cezila.passwordmanager.data.database.PasswordDatabase
import com.cezila.passwordmanager.data.repository.StorePasswordRepositoryImpl
import com.cezila.passwordmanager.domain.repository.StorePasswordRepository
import com.cezila.passwordmanager.domain.use_case.DeletePasswordUseCase
import com.cezila.passwordmanager.domain.use_case.GetPasswordByIdUseCase
import com.cezila.passwordmanager.domain.use_case.GetPasswordsUseCase
import com.cezila.passwordmanager.domain.use_case.InsertPasswordUseCase
import com.cezila.passwordmanager.domain.use_case.PasswordUseCases
import com.cezila.passwordmanager.domain.use_case.SearchPasswordUseCase
import com.cezila.passwordmanager.domain.use_case.UpdatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorePasswordModule {

    @Provides
    @Singleton
    fun provideStorePasswordRepository(
        database: PasswordDatabase
    ): StorePasswordRepository {
        return StorePasswordRepositoryImpl(database.dao)
    }

    @Provides
    @Singleton
    fun providePasswordUseCases(
        repository: StorePasswordRepository,
        encryptionService: EncryptionService
    ): PasswordUseCases {
        return PasswordUseCases(
            insertPasswordUseCase = InsertPasswordUseCase(repository, encryptionService),
            deletePasswordUseCase = DeletePasswordUseCase(repository),
            getPasswordsUseCase = GetPasswordsUseCase(repository),
            getPasswordByIdUseCase = GetPasswordByIdUseCase(repository),
            searchPasswordUseCase = SearchPasswordUseCase(repository),
            updatePasswordUseCase = UpdatePasswordUseCase(repository)
        )
    }

}