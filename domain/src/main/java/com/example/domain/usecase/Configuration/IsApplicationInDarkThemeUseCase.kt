package com.example.domain.usecase.Configuration

import com.example.domain.repository.ConfigurationRepository


/**
 * Return true, if the application in dark theme,
 * false, if the application in light theme,
 * or null, if the application using system theme
 */
class IsApplicationInDarkThemeUseCase(
    private val configurationRepository: ConfigurationRepository
) {

    /**
     * Return true, if the application in dark theme,
     * false, if the application in light theme,
     * or null, if the application using system theme
     */
    suspend operator fun invoke(id: Int = 0) =
            configurationRepository.isApplicationInDarkTheme(id)
}