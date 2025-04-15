package com.example.composablefunctest.ChangeImage

import android.graphics.Bitmap

sealed class ChangeImageEvent {

    data object ChangeBottomSheetState : ChangeImageEvent()
    data object ResetException : ChangeImageEvent()
    data class PickCameraImage(val value: ByteArray?) : ChangeImageEvent()
    data class PickGalleryImage(val value: Bitmap?) : ChangeImageEvent()
}