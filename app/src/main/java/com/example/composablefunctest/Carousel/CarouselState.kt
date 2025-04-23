package com.example.composablefunctest.Carousel

import com.example.composablefunctest.R

data class CarouselState(
    val items: List<Int> = listOf(
        R.drawable.carousel_icon,
        R.drawable.drag_icon,
        R.drawable.custom_layout_icon,
        R.drawable.video_icon,
        R.drawable.notification_icon,
        R.drawable.radio_button_icon,
        R.drawable.gallery_icon,
        R.drawable.camera_icon,
    ),

    val currentItem: Int = 0,
)
