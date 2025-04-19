package com.example.data.di

import android.app.Notification
import android.content.Context
import com.example.data.repository.NotificationRepositoryImpl
import com.example.domain.repository.NotificationRepository
import com.example.domain.usecase.Notification.SendNotificationAfterOneMinuteUseCase
import com.example.domain.usecase.Notification.SendNotificationUseCase
import com.example.domain.usecase.utils.NotificationKeys.CHANNEL_ID
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Provides
    @Singleton
    fun getNotifRepo(@ApplicationContext context: Context, notification: Notification.Builder) : NotificationRepository
        = NotificationRepositoryImpl(context, notification)

    @Provides
    @Singleton
    fun getSendNotifUseCase(notificationRepository: NotificationRepository)
        = SendNotificationUseCase(notificationRepository)
    @Provides
    @Singleton
    fun getSendNotifAfterMinuteUseCase(notificationRepository: NotificationRepository)
        = SendNotificationAfterOneMinuteUseCase(notificationRepository)

    @Provides
    @Singleton
    fun getNotification(@ApplicationContext context: Context)
        = Notification.Builder(context, CHANNEL_ID)
}