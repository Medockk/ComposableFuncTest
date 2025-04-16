package com.example.composablefunctest.Drag

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DragViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(DragState())
    val state: State<DragState> = _state

    fun onEvent(event: DragEvent){
        when (event){
            is DragEvent.SetDragSize -> {
                _state.value = state.value.copy(
                    dragWidth = event.value
                )
            }
        }
    }
}