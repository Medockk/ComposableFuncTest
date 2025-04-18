package com.example.domain.usecase.Notification

import com.example.domain.repository.NotificationRepository

class SendNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {

    suspend operator fun invoke(icon: Int, title: String = "Title", description: String = "Description")
        = notificationRepository.sendNotification(title, description, icon)
}