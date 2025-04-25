package com.example.composablefunctest.Animation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomAnimationBox(
    modifier: Modifier = Modifier
        .size(40.dp)
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
    )
}