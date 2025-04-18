package com.example.data.repository

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.R
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.domain.repository.NotificationRepository

class NotificationRepositoryImpl(
    private val context: Context
) : NotificationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun sendNotification(title: String, description: String, icon: Int) {

        val channel = NotificationChannel(
            "channelId", "channelName", NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            this.description = description
        }

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(context, "channelId")
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(icon)
            .build()

        NotificationManagerCompat.from(context).notify(1, builder)

    }
}