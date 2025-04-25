@file:Suppress("UNCHECKED_CAST")

package com.example.composablefunctest.Animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.Animation.components.AnimationType
import com.example.composablefunctest.Animation.components.CustomAnimationBox
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomButton
import com.example.composablefunctest.common.CustomTextField
import com.example.composablefunctest.common.CustomTopAppBar

@Composable
fun AnimationScreen(
    navController: NavController,
    viewModel: AnimationViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val dropDownMenuContent = listOf(
        listOf(
            stringResource(R.string.fade_in_fade_out),
            {
                viewModel.onEvent(AnimationEvent.ChangeAnimationType(AnimationType.Fade))
            },
        ),
        listOf(
            stringResource(R.string.scale_in_scale_out),
            {
                viewModel.onEvent(AnimationEvent.ChangeAnimationType(AnimationType.Scale))
            },
        ),
        listOf(
            stringResource(R.string.slide_in_slide_out),
            {
                viewModel.onEvent(AnimationEvent.ChangeAnimationType(AnimationType.Slide))
            },
        ),
        listOf(
            stringResource(R.string.slide_in_horizontally_slide_out_horizontally),
            {
                viewModel.onEvent(AnimationEvent.ChangeAnimationType(AnimationType.SlideHorizontally))
            },
        ),
        listOf(
            stringResource(R.string.slide_in_vertically_slide_out_vertically),
            {
                viewModel.onEvent(AnimationEvent.ChangeAnimationType(AnimationType.SlideVertically))
            },
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        CustomTopAppBar(
            title = stringResource(R.string.animation),
            icon = Icons.AutoMirrored.Default.KeyboardArrowLeft
        ) {
            navController.popBackStack()
        }
        Spacer(Modifier.height(30.dp))

        CustomTextField(
            value = state.duration,
            onValueChange = {
                viewModel.onEvent(AnimationEvent.ChangeDuration(it))
            },
            hintText = "Duration of animation (in mills) :)",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            trailingIcon = {
                IconButton(
                    onClick = {
                        viewModel.onEvent(AnimationEvent.ChangeDropDownMenuState)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )

                    DropdownMenu(
                        expanded = state.isDropDownMenuOpened,
                        onDismissRequest = {
                            viewModel.onEvent(AnimationEvent.ChangeDropDownMenuState)
                        }
                    ) {
                        dropDownMenuContent.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = it[0] as String
                                    )
                                },
                                onClick = {
                                    (it[1] as () -> Unit).invoke()
                                    viewModel.onEvent(AnimationEvent.ChangeDropDownMenuState)
                                },
                            )
                        }
                    }
                }
            }
        )

        Spacer(Modifier.height(20.dp))
        Text(
            text = when (state.currentAnimationType){
                AnimationType.Fade -> stringResource(R.string.fade_in_fade_out)
                AnimationType.Scale -> stringResource(R.string.scale_in_scale_out)
                AnimationType.Slide -> stringResource(R.string.slide_in_slide_out)
                AnimationType.SlideHorizontally -> stringResource(R.string.slide_in_horizontally_slide_out_horizontally)
                AnimationType.SlideVertically -> stringResource(R.string.slide_in_vertically_slide_out_vertically)
            },
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(10.dp))

        AnimatedVisibility(
            visible = state.isAnimatedVisibility,
            enter = when (state.currentAnimationType){
                AnimationType.Fade -> {
                    fadeIn(tween(state.duration.toIntOrNull() ?: 0))
                }

                AnimationType.Scale -> {
                    scaleIn(tween(state.duration.toIntOrNull() ?: 0))
                }

                AnimationType.Slide -> {
                    slideIn(tween(state.duration.toIntOrNull() ?: 0)) { IntOffset.VisibilityThreshold }
                }
                AnimationType.SlideHorizontally -> {
                    slideInHorizontally(tween(state.duration.toIntOrNull() ?: 0))
                }
                AnimationType.SlideVertically -> {
                    slideInVertically(tween(state.duration.toIntOrNull() ?: 0))
                }
            },
            exit = when (state.currentAnimationType){
                AnimationType.Fade -> {
                    fadeOut(tween(state.duration.toIntOrNull() ?: 0))
                }

                AnimationType.Scale -> {
                    scaleOut(tween(state.duration.toIntOrNull() ?: 0))
                }

                AnimationType.Slide -> {
                    slideOut(tween(state.duration.toIntOrNull() ?: 0)) { IntOffset.VisibilityThreshold }
                }
                AnimationType.SlideHorizontally -> {
                    slideOutHorizontally(tween(state.duration.toIntOrNull() ?: 0))
                }
                AnimationType.SlideVertically -> {
                    slideOutVertically(tween(state.duration.toIntOrNull() ?: 0))
                }
            }
        ) {
            CustomAnimationBox(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(60.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        CustomButton(
            text = "Start Animation",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(55.dp)
                .padding(horizontal = 20.dp)
        ) {
            viewModel.onEvent(AnimationEvent.ChangeIsVisibilityState)
        }
    }

}