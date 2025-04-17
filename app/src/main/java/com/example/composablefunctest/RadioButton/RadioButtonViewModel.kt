package com.example.composablefunctest.RadioButton

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.UserData.GetRadioButtonTextUseCase
import com.example.domain.usecase.UserData.SetRadioButtonTextUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RadioButtonViewModel @Inject constructor(
    private val getRadioButtonTextUseCase: GetRadioButtonTextUseCase,
    private val setRadioButtonTextUseCase: SetRadioButtonTextUseCase
) : ViewModel() {

    private val _state = mutableStateOf(RadioButtonState())
    val state: State<RadioButtonState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO){
            try {
                getRadioButtonText()
            } catch (_: Exception) {

            }
        }
    }

    private suspend fun getRadioButtonText() {
        val text = getRadioButtonTextUseCase()

        withContext(Dispatchers.Main){
            _state.value = state.value.copy(
                selectedRadioButton = text
            )
        }
    }

    fun onEvent(event: RadioButtonEvent){
        when (event){
            is RadioButtonEvent.SelectRadioButton -> {
                _state.value = state.value.copy(
                    selectedRadioButton = event.value
                )

                viewModelScope.launch(Dispatchers.IO){
                    try {
                        setRadioButtonTextUseCase(event.value)
                    } catch (_: Exception) {

                    }
                }
            }
        }
    }
}