package com.example.composablefunctest.Carousel

sealed class CarouselEvent {

    data class SetCarouselItem(val value: Int) : CarouselEvent()
}