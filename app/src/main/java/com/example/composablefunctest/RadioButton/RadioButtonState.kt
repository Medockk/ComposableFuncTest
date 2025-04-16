package com.example.composablefunctest.RadioButton

data class RadioButtonState(
    val radioButtonList: List<String> = listOf(
        "First", "Second", "Third", "Fourth",
        "Fifth", "Sixth"
    ),

    val selectedRadioButton: String = radioButtonList[0],
)
