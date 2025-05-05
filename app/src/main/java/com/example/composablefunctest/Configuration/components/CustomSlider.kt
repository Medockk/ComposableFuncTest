package com.example.composablefunctest.Configuration.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CustomSlider(
    value: Float,
    modifier: Modifier = Modifier,
    onValueChange: (Float) -> Unit,
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Box(Modifier
            .matchParentSize()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.secondary))
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(value.roundToInt(), 0)
                }
                .size(24.dp)
                .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
                .draggable(
                    state = rememberDraggableState {
                        onValueChange(it)
                    },
                    orientation = Orientation.Horizontal
                )
        )
    }
}