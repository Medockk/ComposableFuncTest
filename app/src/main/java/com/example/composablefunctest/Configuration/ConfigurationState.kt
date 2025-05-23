package com.example.composablefunctest.Configuration

data class ConfigurationState(
    val appTheme: Boolean? = null,
    val exception: String = "",
    val showIndication: Boolean = false,

    val themeClick: (Boolean?) -> Unit = {},
    val currentLanguage: String = "",

    val brightness: Float = 0f,
)

sealed class LanguageList {
    data object English : LanguageList()
    data object Russian : LanguageList()
}