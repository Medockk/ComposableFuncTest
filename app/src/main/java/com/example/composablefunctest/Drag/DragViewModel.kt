package com.example.composablefunctest.Drag

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DragViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(DragState())
    val state: State<DragState> = _state

    fun onEvent(event: DragEvent){
        when (event){
            is DragEvent.SetDragSize -> {
                viewModelScope.launch(Dispatchers.Main){
                    _state.value = state.value.copy(
                        dragWidth = event.value
                    )
                }
            }

            is DragEvent.SetModifierDraggableOffset -> {
                _state.value = state.value.copy(
                    modifierDraggableOffset = event.value + _state.value.modifierDraggableOffset
                )
            }

            is DragEvent.SetModifierTransformable -> {
                _state.value = state.value.copy(
                    modifierTransformableZoom = event.zoom * _state.value.modifierTransformableZoom,
                    modifierTransformableOffset = event.offset + _state.value.modifierTransformableOffset,
                    modifierTransformableRotation = event.rotation + _state.value.modifierTransformableRotation
                )
            }

            is DragEvent.SetPointerInputOffset -> {
                _state.value = state.value.copy(
                    modifierPointerInput = event.value + _state.value.modifierPointerInput
                )
            }
        }
    }
}