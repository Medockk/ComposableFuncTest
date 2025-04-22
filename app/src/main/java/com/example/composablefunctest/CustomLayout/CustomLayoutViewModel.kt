package com.example.composablefunctest.CustomLayout

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CustomLayoutViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(CustomLayoutState())
    val state: State<CustomLayoutState> = _state

    fun onEvent(event: CustomLayoutEvent){
        when (event){
            is CustomLayoutEvent.ChangeOffset -> {
                _state.value = state.value.copy(
                    buttonOffset = _state.value.buttonOffset + event.value
                )
            }

            is CustomLayoutEvent.ChangeSizeWidth -> {
                _state.value = state.value.copy(
                    sizeWidth = event.value
                )
            }
        }
    }
}