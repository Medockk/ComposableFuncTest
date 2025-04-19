package com.example.domain.usecase.Notification

import android.content.Intent
import com.example.domain.repository.NotificationRepository

class SendNotificationAfterOneMinuteUseCase(
    private val notificationRepository: NotificationRepository
) {

    suspend operator fun invoke(
        title: String,
        description: String,
        icon: Int,
        intent: Intent
    )  = notificationRepository.sendNotificationAfterOneMinute(title, description, icon, intent)
}