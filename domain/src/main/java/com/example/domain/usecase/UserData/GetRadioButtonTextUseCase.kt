package com.example.domain.usecase.UserData

import com.example.domain.repository.UserDataRepository

class GetRadioButtonTextUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke() = userDataRepository.getRadioButtonText()
}