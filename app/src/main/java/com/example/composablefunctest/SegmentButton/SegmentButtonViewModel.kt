package com.example.composablefunctest.SegmentButton

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SegmentButtonViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(SegmentButtonState())
    val state: State<SegmentButtonState> = _state

    fun onEvent(event: SegmentButtonEvent){
        when (event){
            is SegmentButtonEvent.SetSingleSegmentButtonIndex -> {
                _state.value = state.value.copy(
                    singleButtonIndex = event.value
                )
            }

            is SegmentButtonEvent.SetMultiSegmentButton -> {
                _state.value = state.value.copy(
                    multiSegmentState = _state.value.multiSegmentState.also {
                        it.set(event.index, event.value)
                    }
                )
            }
        }
    }
}