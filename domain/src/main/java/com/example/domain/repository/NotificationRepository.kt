package com.example.domain.repository

import android.content.Intent

interface NotificationRepository {

    suspend fun sendNotification(
        title: String, description: String, icon: Int,
        intent: Intent
    )

    suspend fun sendNotificationAfterOneMinute(
        title: String,
        description: String,
        icon: Int,
        intent: Intent
    )
}