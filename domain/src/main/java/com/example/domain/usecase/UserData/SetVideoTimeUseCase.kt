package com.example.domain.usecase.UserData

import com.example.domain.repository.UserDataRepository

class SetVideoTimeUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke(time: Long) = userDataRepository.setVideoTime(time)
}