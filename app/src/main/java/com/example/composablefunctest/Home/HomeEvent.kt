package com.example.composablefunctest.Home

sealed class HomeEvent {

    data object ResetException: HomeEvent()
}