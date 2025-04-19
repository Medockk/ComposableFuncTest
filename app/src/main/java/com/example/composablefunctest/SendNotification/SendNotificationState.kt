package com.example.composablefunctest.SendNotification

import android.app.Activity

data class SendNotificationState(
    val notificationTitle: String = "",
    val notificationDescription: String = "",
    val activity: Activity? = null,
)
