package com.example.domain.usecase.UserData

import com.example.domain.repository.UserDataRepository

class SetCarouselItemUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke(item: Int) =
            userDataRepository.setCarouselItem(item)
}