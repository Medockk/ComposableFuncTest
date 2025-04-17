package com.example.domain.usecase.UserData

import com.example.domain.repository.UserDataRepository

class SetRadioButtonTextUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke(text: String) = userDataRepository.setRadioButtonText(text)
}