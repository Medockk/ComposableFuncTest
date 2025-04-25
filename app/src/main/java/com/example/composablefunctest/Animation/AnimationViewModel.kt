package com.example.composablefunctest.Animation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimationViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(AnimationState())
    val state: State<AnimationState> = _state

    fun onEvent(event: AnimationEvent){
        when (event){
            AnimationEvent.ChangeIsVisibilityState -> {
                _state.value = state.value.copy(
                    isAnimatedVisibility = !_state.value.isAnimatedVisibility
                )
            }

            is AnimationEvent.ChangeDuration -> {
                _state.value = state.value.copy(
                    duration = event.value
                )
            }

            AnimationEvent.ChangeDropDownMenuState -> {
                _state.value = state.value.copy(
                    isDropDownMenuOpened = !_state.value.isDropDownMenuOpened
                )
            }

            is AnimationEvent.ChangeAnimationType -> {
                _state.value = state.value.copy(
                    currentAnimationType = event.value
                )
            }
        }
    }
}