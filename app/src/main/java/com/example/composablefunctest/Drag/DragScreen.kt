@file:OptIn(ExperimentalFoundationApi::class)

package com.example.composablefunctest.Drag

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.Drag.components.CustomDraggableBox
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomTopAppBar
import kotlin.math.roundToInt

@SuppressLint("RememberReturnType")
@Composable
fun DragScreen(
    navController: NavController,
    viewModel: DragViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val anchoredDragState = remember {
        mutableStateOf(
            AnchoredDraggableState(
                initialValue = DragAnchors.START,
                anchors = DraggableAnchors {
                    DragAnchors.START at 0f
                    DragAnchors.END at 0f
                },
                positionalThreshold = { t: Float ->
                    t * 0.7f
                },
                velocityThreshold = {
                    Float.POSITIVE_INFINITY
                },
                snapAnimationSpec = tween(),
                decayAnimationSpec = decayAnimationSpec
            )
        )
    }

    LaunchedEffect(anchoredDragState.value.currentValue) {
        if (anchoredDragState.value.currentValue == DragAnchors.END){
            Log.e("e", "eeeeeeee")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        CustomTopAppBar(
            title = stringResource(R.string.drag_screen),
            icon = null
        ) {
            navController.popBackStack()
        }

        Spacer(Modifier.height(30.dp))

        Text(
            text = stringResource(R.string.anchored_drag),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(20.dp)
                .onSizeChanged {
                    val width = (it.width - 20).toFloat()
                    anchoredDragState.value =
                        AnchoredDraggableState(
                            initialValue = DragAnchors.START,
                            anchors = DraggableAnchors {
                                DragAnchors.START at 0f
                                DragAnchors.END at width
                            },
                            positionalThreshold = { t: Float ->
                                t * 0.5f
                            },
                            velocityThreshold = {
                                Float.POSITIVE_INFINITY
                            },
                            snapAnimationSpec = snap(),
                            decayAnimationSpec = decayAnimationSpec
                        )
                }
                .anchoredDraggable(anchoredDragState.value, Orientation.Horizontal),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.onPrimary.copy(0.3f))
            )
            CustomDraggableBox(
                Modifier
                    .offset {
                        IntOffset(
                            anchoredDragState.value.requireOffset().roundToInt(), 0
                        )
                    }
            )
        }

        Spacer(Modifier.height(30.dp))

        Text(
            text = stringResource(R.string.modifier_draggable),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .draggable(
                    state = rememberDraggableState {
                        viewModel.onEvent(DragEvent.SetModifierDraggableOffset(it))
                    },
                    orientation = Orientation.Horizontal
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        MaterialTheme.colorScheme.onPrimary.copy(0.3f)
                    )
            )
            CustomDraggableBox(
                Modifier
                    .offset {
                        IntOffset(
                            state.modifierDraggableOffset.roundToInt(),
                            0
                        )
                    }
            )
        }

        Spacer(Modifier.height(30.dp))

        Text(
            text = stringResource(R.string.modifier_multi_touch_gesture),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            val transformableState =
                rememberTransformableState { zoomChange, offsetChange, rotationChange ->
                    Log.e("zoom", zoomChange.toString())
                    viewModel.onEvent(
                        DragEvent.SetModifierTransformable(
                            zoomChange,
                            offsetChange,
                            rotationChange
                        )
                    )
                }
            CustomDraggableBox(
                Modifier
                    .sizeIn(40.dp)
                    .heightIn(40.dp)
                    .graphicsLayer {
                        scaleX = state.modifierTransformableZoom
                        scaleY = state.modifierTransformableZoom
                        rotationZ = state.modifierTransformableRotation
                        translationX = state.modifierTransformableOffset.x
                        translationY = state.modifierTransformableOffset.y
                    }
                    .transformable(transformableState)
            )
        }

        Spacer(Modifier.height(30.dp))
        Text(
            text = stringResource(R.string.modifier_pointer_input),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(10.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            CustomDraggableBox(
                Modifier
                    .size(40.dp)
                    .offset {
                        IntOffset(
                            state.modifierPointerInput.x.roundToInt(),
                            state.modifierPointerInput.y.roundToInt(),
                        )
                    }
                    .pointerInput(Unit) {
                        this.detectDragGesturesAfterLongPress { _, dragAmount ->
                            viewModel.onEvent(DragEvent.SetPointerInputOffset(dragAmount))
                        }
                    })
        }
    }
}

private enum class DragAnchors {
    START,
    END
}