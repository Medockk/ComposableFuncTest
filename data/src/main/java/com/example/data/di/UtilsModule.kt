package com.example.data.di

import com.example.domain.usecase.utils.ConvertImageType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun getConvertImageType() = ConvertImageType()
}