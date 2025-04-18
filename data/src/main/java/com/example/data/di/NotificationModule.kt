package com.example.data.di

import android.app.Activity
import android.content.Context
import com.example.data.repository.NotificationRepositoryImpl
import com.example.domain.repository.NotificationRepository
import com.example.domain.usecase.Notification.SendNotificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun getNotifRepo(@ApplicationContext context: Context) : NotificationRepository
        = NotificationRepositoryImpl(context)

    @Provides
    @Singleton
    fun getSendNotifUseCase(notificationRepository: NotificationRepository)
        = SendNotificationUseCase(notificationRepository)

}