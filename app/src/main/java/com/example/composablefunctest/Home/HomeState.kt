package com.example.composablefunctest.Home

import android.graphics.Bitmap

data class HomeState(
    val isModalDrawerSheetOpen: Boolean = false,
    val userImage: Bitmap? = null,
    val exception: String = "",
    val userName: String = "",
)
