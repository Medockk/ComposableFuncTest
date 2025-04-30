package com.example.composablefunctest.Configuration

data class ConfigurationState(
    val appTheme: Boolean? = null,
    val exception: String = "",
    val showIndication: Boolean = false,

    val themeClick: (Boolean?) -> Unit = {},
)