package com.example.composablefunctest.Drag

import androidx.compose.ui.geometry.Offset

sealed class DragEvent {

    data class SetDragSize(val value: Int) : DragEvent()
    data class SetModifierDraggableOffset(val value: Float) : DragEvent()
    data class SetModifierTransformable(val zoom: Float,
        val offset: Offset, val rotation: Float) : DragEvent()

    data class SetPointerInputOffset(val value: Offset) : DragEvent()
}