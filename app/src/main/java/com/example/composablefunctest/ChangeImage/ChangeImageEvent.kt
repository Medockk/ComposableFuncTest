package com.example.composablefunctest.ChangeImage

sealed class ChangeImageEvent {

    data object ChangeBottomSheetState : ChangeImageEvent()
}