package com.example.composablefunctest.RadioButton

sealed class RadioButtonEvent {

    data class SelectRadioButton(val value: String) : RadioButtonEvent()
}