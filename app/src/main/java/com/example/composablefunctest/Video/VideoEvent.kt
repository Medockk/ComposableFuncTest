package com.example.composablefunctest.Video

sealed class VideoEvent {

    data class SetVideoTime(val value: Long) : VideoEvent()
    data object ResetException : VideoEvent()
}