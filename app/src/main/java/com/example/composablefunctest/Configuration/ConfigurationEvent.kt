package com.example.composablefunctest.Configuration

import android.app.Activity

sealed class ConfigurationEvent {

    /**
     * @param theme true, when the app in dark theme,
     * false, when the app in light theme,
     * or null, when the app using the system theme
     */
    data class SetTheme(val theme: Boolean?) : ConfigurationEvent()
    data class SetChangeThemeClick(val onClick: (Boolean?) -> Unit) : ConfigurationEvent()

    data class SetLanguage(val value: LanguageList) : ConfigurationEvent()
    data class ChangeOrientation(
        val activity: Activity?, val orientations: Int
    ) : ConfigurationEvent()
    data class ChangeBrightness(val value: Float) : ConfigurationEvent()
}