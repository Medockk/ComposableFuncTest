package com.example.composablefunctest.Drag

import androidx.compose.ui.geometry.Offset

data class DragState(
    val dragWidth: Int = 0,
    val modifierDraggableOffset: Float = 0f,
    val modifierTransformableRotation: Float = 0f,
    val modifierTransformableOffset: Offset = Offset.Zero,
    val modifierTransformableZoom: Float = 1f,

    val modifierPointerInput: Offset = Offset.Zero,
)
