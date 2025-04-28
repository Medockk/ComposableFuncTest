package com.example.domain.usecase.Configuration

import com.example.domain.repository.ConfigurationRepository

/**
 * @param theme true, if the app in dark theme,
 * false, if the app in light theme,
 * or null, if the app using system theme
 */
class SetApplicationThemeUseCase(
    private val configurationRepository: ConfigurationRepository
) {

    /**
     * @param theme true, if the app in dark theme,
     * false, if the app in light theme,
     * or null, if the app using system theme
     */
    suspend operator fun invoke(theme: Boolean?, id: Int = 0) =
        configurationRepository.setApplicationTheme(theme, id)
}