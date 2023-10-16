package com.cezila.ksafe.di

import com.cezila.ksafe.domain.use_case.GeneratePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeneratePasswordModule {

    @Provides
    @Singleton
    fun provideGeneratePasswordUseCase(): GeneratePasswordUseCase {
        return GeneratePasswordUseCase()
    }

}