package com.example.composablefunctest.CustomLayout

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomTopAppBar
import kotlin.math.roundToInt

@Composable
fun CustomLayoutScreen(
    navController: NavController,
    viewModel: CustomLayoutViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        CustomTopAppBar(
            title = stringResource(R.string.custom_layout_screen),
            icon = null
        ) {
            navController.popBackStack()
        }
        Spacer(Modifier.height(30.dp))

        Layout(
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(55.dp)
                        .padding(horizontal = 20.dp)
                        .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(10.dp)),
                )
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(state.buttonOffset.roundToInt(), 0)
                        }
                        .layoutId(LayoutIDs.ButtonId)
                        .padding(3.dp)
                        .width(50.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .draggable(rememberDraggableState {
                            viewModel.onEvent(CustomLayoutEvent.ChangeOffset(it))
                        }, Orientation.Horizontal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                    )
                }
                Box(
                    Modifier
                        .layoutId(LayoutIDs.LayoutEndId)
                        .align(Alignment.End)
                )
            },
            measurePolicy = { measurables: List<Measurable>, constraint ->

                val place = measurables.map { it.measure(constraint) }

                layout(constraint.maxWidth, constraint.maxHeight) {

                    place.forEach {
                        it.placeRelative(0,0)
                    }
                }
            }
        )
    }
}