package com.example.domain.repository

interface NotificationRepository {

    suspend fun sendNotification(title: String, description: String, icon: Int)
}