package com.example.data.repository

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.Notification.Action
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
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

    private val _notificationAssistant = NotificationAssistant(context)

    @SuppressLint("MissingPermission")
    override suspend fun sendNotification(
        title: String,
        description: String,
        icon: Int,
        intent: Intent
    ) {

        val pendingHomeIntent = PendingIntent.getActivity(
            context, 0, intent.apply { action = ACTION_HOME }, PendingIntent.FLAG_IMMUTABLE
        )
        val pendingNameIntent = PendingIntent.getActivity(
            context, 0, intent.apply { action = ACTION_NAME }, PendingIntent.FLAG_IMMUTABLE
        )

        val homeAction = Action.Builder(
            icon, ACTION_HOME, pendingHomeIntent
        ).build()
        val nameAction = Action.Builder(
            icon, ACTION_NAME, pendingNameIntent
        ).build()

        _notificationAssistant.sendNotification(
            title, description, icon, listOf(homeAction, nameAction)
        )

    }

    override suspend fun sendNotificationAfterOneMinute(
        title: String,
        description: String,
        icon: Int,
        intent: Intent
    ) {
        ICON = icon

        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            Intent(context, AlarmReceiver::class.java)
                .putExtra("title", title)
                .putExtra("description", description),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.SECOND, LocalDateTime.now().second + 5)
        }

        manager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }
}

class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null && intent != null) {
            val notificationAssistant = NotificationAssistant(context)

            notificationAssistant.sendNotification(
                title = intent.extras?.getString("title") ?: "TITLE",
                description = intent.extras?.getString("description") ?: "DESCRIPTION"
            )

        } else if (context == null) {
            Log.e("context", "context")
        } else {
            Log.e("intent", "intent")
        }
    }
}

private class NotificationAssistant(
    private val context: Context
) {

    private val _manager = context.getSystemService(NotificationManager::class.java)

    init {
        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        _manager.createNotificationChannel(channel)
    }

    fun sendNotification(
        title: String,
        description: String,
        icon: Int? = null,
        intent: Intent? = null
    ) {
        val builder = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(icon ?: ICON)
            .setContentTitle(title)
            .setContentText(description)
            .apply {
                if (intent != null) {
                    setContentIntent(
                        PendingIntent.getActivity(
                            context,
                            1,
                            intent,
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                }
            }
            .build()

        _manager.notify(1, builder)
    }

    fun sendNotification(
        title: String,
        description: String,
        icon: Int? = null,
        intents: List<Action>
    ) {
        val builder = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(icon ?: ICON)
            .setContentTitle(title)
            .setContentText(description)
            .apply {
                intents.forEach {
                    addAction(it)
                }
            }
            .build()

        _manager.notify(1, builder)
    }
}