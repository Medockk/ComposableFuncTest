package com.example.domain.usecase.UserData

import com.example.domain.repository.UserDataRepository

class ChangeUserImageUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke(newImage: ByteArray) = userDataRepository.changeUserImage(newImage)
}