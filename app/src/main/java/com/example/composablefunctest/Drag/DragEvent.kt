package com.example.composablefunctest.Drag

sealed class DragEvent {

    data class SetDragSize(val value: Int) : DragEvent()
}