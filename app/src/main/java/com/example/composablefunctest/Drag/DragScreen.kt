@file:OptIn(ExperimentalFoundationApi::class)

package com.example.composablefunctest.Drag

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.snap
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
    val density = LocalDensity.current
    val conf = LocalConfiguration.current.screenHeightDp.dp - 40.dp
    val  w = with(density){conf.toPx()}
    val width = remember { mutableStateOf((0f)) }
    val t = 1000f

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val anchoredDragState = remember {
        mutableStateOf(
            AnchoredDraggableState(
                initialValue = DragAnchors.START,
                anchors = DraggableAnchors {
                    DragAnchors.START at 0f
                    DragAnchors.CENTER at 0 / 2f
                    DragAnchors.END at 0f
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
        )
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
                    anchoredDragState.value =
                        AnchoredDraggableState(
                            initialValue = DragAnchors.START,
                            anchors = DraggableAnchors {
                                DragAnchors.START at 0f
                                DragAnchors.CENTER at it.width / 2f
                                DragAnchors.END at it.width.toFloat()
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
                    Log.e("onSize", it.width.toString())
                }
                .anchoredDraggable(anchoredDragState.value, Orientation.Horizontal),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Black.copy(0.3f))
            )
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            anchoredDragState.value.requireOffset().roundToInt(), 0
                        )
                    }
                    .size(20.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
        }
    }
}

private enum class DragAnchors {
    START,
    CENTER,
    END
}