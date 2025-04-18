package com.example.composablefunctest.SendNotification

sealed class SendNotificationEvent {

    data class EnterTitle(val value: String) : SendNotificationEvent()
    data class EnterDescription(val value: String) : SendNotificationEvent()

    data object SendNotificationClick: SendNotificationEvent()
}