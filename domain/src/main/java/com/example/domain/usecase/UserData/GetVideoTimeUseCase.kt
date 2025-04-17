package com.example.domain.usecase.UserData

import com.example.domain.repository.UserDataRepository

class GetVideoTimeUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke() = userDataRepository.getVideoTime()
}