package com.example.composablefunctest.Carousel

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
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
    val pagerState = rememberPagerState(
        initialPage = state.currentItem
    ) { state.items.size }
    Log.e("data1", state.currentItem.toString())
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        pagerState.scrollToPage(state.currentItem)
    }

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

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            pageSpacing = 20.dp,
        ) { item ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
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
                    .weight(1f)
            ) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        state.currentItem - 1,
                        animationSpec = tween(700)
                    )
                }

                viewModel.onEvent(CarouselEvent.SetCarouselItem(state.currentItem - 1))
            }
            Spacer(Modifier.width(15.dp))
            CustomButton(
                text = "Next",
                modifier = Modifier
                    .weight(1f)
            ) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        state.currentItem + 1,
                        animationSpec = tween(700)
                    )
                }

                viewModel.onEvent(CarouselEvent.SetCarouselItem(state.currentItem + 1))
            }
        }

        Spacer(Modifier.height(30.dp))
    }
}