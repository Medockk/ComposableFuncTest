package com.example.composablefunctest.Configuration.components

import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CustomSlider(
    value: Float,
    modifier: Modifier = Modifier,
    onValueChange: (Float) -> Unit,
) {
    val size = remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .onSizeChanged {
                size.value = it.width.toFloat()
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(Modifier
            .fillMaxWidth()
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
                        if (0f <= value+it && value+it <= size.value){
                            onValueChange(it)
                        }
                    },
                    orientation = Orientation.Horizontal
                )
        )
    }
}