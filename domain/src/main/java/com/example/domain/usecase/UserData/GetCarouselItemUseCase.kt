package com.example.domain.usecase.UserData

import com.example.domain.repository.UserDataRepository

class GetCarouselItemUseCase(
    private val userDataRepository: UserDataRepository
) {

    suspend operator fun invoke() =
            userDataRepository.getCarouselItem()
}