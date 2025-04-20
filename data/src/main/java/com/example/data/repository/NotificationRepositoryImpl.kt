package com.example.data.repository

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.domain.repository.NotificationRepository
import com.example.domain.usecase.utils.NotificationKeys.ACTION_HOME
import com.example.domain.usecase.utils.NotificationKeys.ACTION_NAME
import com.example.domain.usecase.utils.NotificationKeys.CHANNEL_ID
import com.example.domain.usecase.utils.NotificationKeys.CHANNEL_NAME
import java.time.LocalDateTime
import java.util.Calendar

private var ICON: Int = 0

class NotificationRepositoryImpl(
    private val context: Context,
    private val notification: Notification.Builder,
    private val notificationChannel: NotificationChannel
) : NotificationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun sendNotification(
        title: String,
        description: String,
        icon: Int,
        intent: Intent
    ) {

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(notificationChannel)

        val pendingHomeIntent = PendingIntent.getActivity(
            context, 0, intent.apply { action = ACTION_HOME }, PendingIntent.FLAG_IMMUTABLE
        )
        val pendingNameIntent = PendingIntent.getActivity(
            context, 0, intent.apply { action = ACTION_NAME }, PendingIntent.FLAG_IMMUTABLE
        )

        val homeAction = Notification.Action.Builder(
            icon, ACTION_HOME, pendingHomeIntent
        ).build()
        val nameAction = Notification.Action.Builder(
            icon, ACTION_NAME, pendingNameIntent
        ).build()

        val builder = notification
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(icon)
            .setAutoCancel(true)
            .addAction(homeAction)
            .addAction(nameAction)
            .build()

        NotificationManagerCompat.from(context).notify(1, builder)

    }

    override suspend fun sendNotificationAfterOneMinute(
        title: String,
        description: String,
        icon: Int,
        intent: Intent
    ) {
        ICON = icon

        val manager = context.getSystemService(AlarmManager::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.SECOND, LocalDateTime.now().second + 10)
        }

        manager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        Log.e("calendar", calendar.toString())
    }
}

private class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.e("receiver", "notif!!")

        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        context?.getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        val manager = context?.getSystemService(NotificationManager::class.java)

        val notification = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(ICON)
            .setContentTitle("title")
            .setContentText("text")
            .build()

        NotificationManagerCompat.from(context!!).notify(1, notification)
        manager?.notify(1, notification)
    }
}