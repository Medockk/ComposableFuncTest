package com.example.composablefunctest.ChangeImage

import android.graphics.Bitmap

data class ChangeImageState(
    val exception: String = "",
    val isModalBottomSheetOpen: Boolean = true,

    val image: Bitmap? = null,
)
