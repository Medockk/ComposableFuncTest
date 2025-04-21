package com.example.composablefunctest.CustomLayout

sealed class CustomLayoutEvent {

    data class ChangeOffset(val value: Float) : CustomLayoutEvent()
}