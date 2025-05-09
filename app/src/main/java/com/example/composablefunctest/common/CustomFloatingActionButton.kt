package com.example.composablefunctest.common

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CustomFloatingActionButton(
    modifier: Modifier = Modifier,
    buttonIcon: ImageVector = Icons.Default.Add,
    shape: Shape = CircleShape,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        shape = shape,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Icon(
            imageVector = buttonIcon,
            contentDescription = null,
            tint = Color.White,
        )
    }
}