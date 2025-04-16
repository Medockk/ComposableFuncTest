package com.example.composablefunctest.ChangeName

import android.graphics.Bitmap

data class ChangeNameState(
    val exception: String = "",
    val userImage: Bitmap? = null,
    val userName: String = "",

    val isChangeSaved: Boolean = true,
    val isSuccessfulSavingChanges: Boolean = false,
)
