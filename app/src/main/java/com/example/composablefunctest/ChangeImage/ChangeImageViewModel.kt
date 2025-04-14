package com.example.composablefunctest.ChangeImage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeImageViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(ChangeImageState())
    val state: State<ChangeImageState> = _state

    fun onEvent(event: ChangeImageEvent){
        when (event){
            ChangeImageEvent.ChangeBottomSheetState -> {
                _state.value = state.value.copy(
                    isModalBottomSheetOpen = !_state.value.isModalBottomSheetOpen
                )
            }
        }
    }
}