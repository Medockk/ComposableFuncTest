package com.example.data.di

import android.content.Context
import com.example.data.data_source.local.dao.ConfigurationDao
import com.example.data.data_source.local.database.ConfigurationDatabase
import com.example.data.repository.ConfigurationRepositoryImpl
import com.example.domain.repository.ConfigurationRepository
import com.example.domain.usecase.Configuration.IsApplicationInDarkThemeUseCase
import com.example.domain.usecase.Configuration.SetApplicationThemeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConfigurationModule {

    @Provides
    @Singleton
    fun getConfDao(@ApplicationContext context: Context) =
        ConfigurationDatabase.createDatabase(context).confDao

    @Provides
    @Singleton
    fun getConfRepository(configurationDao: ConfigurationDao) : ConfigurationRepository
        = ConfigurationRepositoryImpl(configurationDao)
    @Provides
    @Singleton
    fun getIsAppInDarkThemeUseCase(configurationRepository: ConfigurationRepository)
        = IsApplicationInDarkThemeUseCase(configurationRepository)
    @Provides
    @Singleton
    fun getSetAppThemeUseCase(configurationRepository: ConfigurationRepository)
        = SetApplicationThemeUseCase(configurationRepository)
}