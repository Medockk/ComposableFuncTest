@file:OptIn(ExperimentalFoundationApi::class)

package com.example.composablefunctest.CustomLayout.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.composablefunctest.ui.theme.mainColor
import kotlin.math.roundToInt

@SuppressLint("RememberReturnType")
@Composable
fun CustomSwipeButton(
    endLayoutValue: Float,
    modifier: Modifier = Modifier,
    endContent: @Composable (progress: Float, currentPosition: CustomSwipeButton) -> Unit,
    centerContent: @Composable (progress: Float, currentPosition: CustomSwipeButton) -> Unit,
    trackContent: @Composable (progress: Float, currentPosition: CustomSwipeButton) -> Unit,
) {


    val decay = rememberSplineBasedDecay<Float>()
    val density = LocalDensity.current
    val thumbSize = 50f

    val state = remember {
        AnchoredDraggableState(
            initialValue = CustomSwipeButton.START,
            anchors = DraggableAnchors {
                CustomSwipeButton.START at 0f
                CustomSwipeButton.END at endLayoutValue
            },
            positionalThreshold = {
                it * 0.8f
            },
            velocityThreshold = {
                with(density) {
                    700.dp.toPx()
                }
            },
            snapAnimationSpec = tween(700),
            decayAnimationSpec = decay,
        )
    }

    LaunchedEffect(endLayoutValue) {
        state.updateAnchors(
            DraggableAnchors {
                CustomSwipeButton.START at 0f
                CustomSwipeButton.END at endLayoutValue
            }
        )
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                Color.Gray.copy(0.7f),
                RoundedCornerShape(10.dp)
            )
            .drawWithCache {

                if (state.requireOffset() == 0f) {
                    onDrawBehind { }
                }

                onDrawBehind {
                    drawRoundRect(
                        mainColor,
                        cornerRadius = CornerRadius(10f),
                        size = Size(state.requireOffset() + thumbSize + 10f, size.height),
                    )
                }

            },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .anchoredDraggable(state, Orientation.Horizontal)
                .offset {
                    IntOffset(state.requireOffset().roundToInt(), 0)
                }
                .size(thumbSize.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            trackContent(
                state.progress(CustomSwipeButton.START, CustomSwipeButton.END),
                state.currentValue
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            centerContent(
                state.progress(CustomSwipeButton.START, CustomSwipeButton.END),
                state.currentValue
            )
        }


        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 5.dp)
        ) {
            endContent(
                state.progress(CustomSwipeButton.START, CustomSwipeButton.END),
                state.currentValue
            )
        }
    }
}

enum class CustomSwipeButton {
    START,
    END
}