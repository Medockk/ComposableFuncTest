package com.example.data.di

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.data.repository.NotificationRepositoryImpl
import com.example.domain.repository.NotificationRepository
import com.example.domain.usecase.Notification.SendNotificationAfterOneMinuteUseCase
import com.example.domain.usecase.Notification.SendNotificationUseCase
import com.example.domain.usecase.utils.NotificationKeys.CHANNEL_ID
import com.example.domain.usecase.utils.NotificationKeys.CHANNEL_NAME
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
    fun getNotifRepo(@ApplicationContext context: Context,
                     notification: Notification.Builder,
                     notificationChannel: NotificationChannel) : NotificationRepository
        = NotificationRepositoryImpl(context, notification, notificationChannel)

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
    fun getNotificationBuilder(@ApplicationContext context: Context)
        = Notification.Builder(context, CHANNEL_ID)
    @Provides
    @Singleton
    fun getNotificationChannel(@ApplicationContext context: Context) : NotificationChannel{
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        return channel
    }
}