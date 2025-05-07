package com.example.composablefunctest.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CustomIndicator(
    showIndicator: Boolean
) {
    AnimatedVisibility(
        visible = showIndicator,
        enter = fadeIn(tween()),
        exit = fadeOut(tween())
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x1AF7F7F7)),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}