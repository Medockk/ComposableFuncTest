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
        }
    }
}