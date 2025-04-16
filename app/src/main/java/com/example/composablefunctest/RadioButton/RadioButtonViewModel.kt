package com.example.composablefunctest.RadioButton

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RadioButtonViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(RadioButtonState())
    val state: State<RadioButtonState> = _state

    fun onEvent(event: RadioButtonEvent){
        when (event){
            is RadioButtonEvent.SelectRadioButton -> {
                _state.value = state.value.copy(
                    selectedRadioButton = event.value
                )
            }
        }
    }
}