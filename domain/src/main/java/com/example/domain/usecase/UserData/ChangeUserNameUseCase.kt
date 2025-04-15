package com.example.domain.usecase.UserData

import com.example.domain.repository.UserDataRepository

class ChangeUserNameUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke(userName: String) = userDataRepository.changeUserName(userName)
}