package com.cezila.ksafe.di

import com.cezila.ksafe.core.encryption.EncryptionService
import com.cezila.ksafe.data.database.PasswordDatabase
import com.cezila.ksafe.data.repository.StorePasswordRepositoryImpl
import com.cezila.ksafe.domain.repository.StorePasswordRepository
import com.cezila.ksafe.domain.use_case.DeletePasswordUseCase
import com.cezila.ksafe.domain.use_case.GetPasswordByIdUseCase
import com.cezila.ksafe.domain.use_case.GetPasswordsUseCase
import com.cezila.ksafe.domain.use_case.InsertPasswordUseCase
import com.cezila.ksafe.domain.use_case.PasswordUseCases
import com.cezila.ksafe.domain.use_case.SearchPasswordUseCase
import com.cezila.ksafe.domain.use_case.UpdatePasswordUseCase
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