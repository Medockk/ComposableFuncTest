package com.example.composablefunctest.CustomLayout

sealed class CustomLayoutEvent {

    data class ChangeOffset(val value: Float) : CustomLayoutEvent()
    data class ChangeSizeWidth(val value: Float) : CustomLayoutEvent()
    data class ChangeIsSwipeEndedState(val value: Boolean): CustomLayoutEvent()
}