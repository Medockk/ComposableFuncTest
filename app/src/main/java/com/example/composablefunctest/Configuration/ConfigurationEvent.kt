package com.example.composablefunctest.Configuration

sealed class ConfigurationEvent {

    /**
     * @param theme true, when the app in dark theme,
     * false, when the app in light theme,
     * or null, when the app using the system theme
     */
    data class SetTheme(val theme: Boolean?) : ConfigurationEvent()
}