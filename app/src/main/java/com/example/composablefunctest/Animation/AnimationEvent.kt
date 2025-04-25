package com.example.composablefunctest.Animation

import com.example.composablefunctest.Animation.components.AnimationType

sealed class AnimationEvent {

    data class ChangeDuration(val value: String) : AnimationEvent()
    data class ChangeAnimationType(val value: AnimationType) : AnimationEvent()
    data object ChangeIsVisibilityState: AnimationEvent()
    data object ChangeDropDownMenuState: AnimationEvent()
}