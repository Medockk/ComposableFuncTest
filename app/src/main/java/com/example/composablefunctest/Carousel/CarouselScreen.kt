@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composablefunctest.Carousel

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomButton
import com.example.composablefunctest.common.CustomTopAppBar
import kotlinx.coroutines.launch

@Composable
fun CarouselScreen(
    navController: NavController,
    viewModel: CarouselViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val carouselState = rememberCarouselState(state.currentItem) { state.items.size }
    val coroutineScope = rememberCoroutineScope()
    val itemWidth = remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        CustomTopAppBar(
            title = stringResource(R.string.carousel),
            icon = Icons.AutoMirrored.Default.KeyboardArrowLeft
        ) {
            navController.popBackStack()
        }

        Spacer(Modifier.height(30.dp))

        HorizontalMultiBrowseCarousel(
            state = carouselState,
            preferredItemWidth = 5000.dp,
            maxSmallItemWidth = 0.dp,
            minSmallItemWidth = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) { item ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .onSizeChanged { itemWidth.value = it.width.toFloat() }
                    .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(state.items[item]),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit

                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CustomButton(
                text = "Preview",
                modifier = Modifier
                    .weight(1f),
                enabled = !carouselState.isScrollInProgress
            ) {
                coroutineScope.launch {
                    carouselState.animateScrollBy(
                        -itemWidth.value,
                        tween(700)
                    )
                }

                viewModel.onEvent(CarouselEvent.SetCarouselItem(state.currentItem - 1))
            }
            Spacer(Modifier.width(15.dp))
            CustomButton(
                text = "Next",
                modifier = Modifier
                    .weight(1f),
                enabled = !carouselState.isScrollInProgress
            ) {
                coroutineScope.launch {
                    carouselState.animateScrollBy(
                        itemWidth.value,
                        tween(700)
                    )
                }

                viewModel.onEvent(CarouselEvent.SetCarouselItem(state.currentItem + 1))
            }
        }

        Spacer(Modifier.height(30.dp))
    }
}