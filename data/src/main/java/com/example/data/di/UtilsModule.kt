package com.example.data.di

import android.content.Context
import com.example.domain.usecase.utils.WriteSettings
import com.example.domain.usecase.utils.ConvertImageType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun getConvertImageType() = ConvertImageType()
    @Provides
    @Singleton
    fun canWriteSettings(@ApplicationContext context: Context) = WriteSettings(context)
}