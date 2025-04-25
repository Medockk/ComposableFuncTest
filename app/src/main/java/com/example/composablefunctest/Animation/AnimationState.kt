package com.example.composablefunctest.Animation

import com.example.composablefunctest.Animation.components.AnimationType

data class AnimationState(

    val duration: String = "",
    val currentAnimationType: AnimationType = AnimationType.Fade,
    val isDropDownMenuOpened: Boolean = false,
    val isAnimatedVisibility: Boolean = true,
)