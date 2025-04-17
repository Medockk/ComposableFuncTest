package com.example.composablefunctest.Drag.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomDraggableBox(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(20.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
    )
}