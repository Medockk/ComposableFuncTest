package com.example.composablefunctest.SendNotification

import android.app.Activity

sealed class SendNotificationEvent {

    data class EnterTitle(val value: String) : SendNotificationEvent()
    data class EnterDescription(val value: String) : SendNotificationEvent()
    data class SetActivity(val value: Activity?) : SendNotificationEvent()

    data object SendNotificationClick: SendNotificationEvent()
    data object SendNotificationAfterOneMinuteClick: SendNotificationEvent()
}