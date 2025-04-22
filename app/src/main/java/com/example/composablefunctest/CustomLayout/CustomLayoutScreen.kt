package com.example.composablefunctest.CustomLayout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.CustomLayout.components.CustomSwipeButton
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomTopAppBar

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

        CustomSwipeButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .onSizeChanged {
                    viewModel.onEvent(CustomLayoutEvent.ChangeSizeWidth(it.width.toFloat()))
                },
            endLayoutValue = state.sizeWidth,
            trackContent = { _, currentPosition ->

                val icon = when (currentPosition) {
                    CustomSwipeButton.START -> Icons.AutoMirrored.Filled.ArrowForward
                    CustomSwipeButton.END -> Icons.Default.Check
                }

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            endContent = { progress, currentPosition ->

                val endContent = when (currentPosition) {
                    CustomSwipeButton.START -> {
                        if (progress == 1f) 0f
                        else progress
                    }

                    CustomSwipeButton.END -> {
                        progress
                    }
                }

                Row {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .alpha(1 - endContent)
                    )
                    Spacer(Modifier.width(15.dp))
                }

            },
            centerContent = { progress, currentPosition ->

                val opacity = when (currentPosition) {
                    CustomSwipeButton.START -> {
                        if (progress == 1f) {
                            0f
                        } else {
                            progress
                        }
                    }

                    CustomSwipeButton.END -> 1f
                }


                Text(
                    text = stringResource(R.string.swipe_to_confirm),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.alpha(1 - opacity)
                )
            }
        )
    }
}