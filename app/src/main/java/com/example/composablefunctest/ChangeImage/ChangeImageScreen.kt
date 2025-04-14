@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("UNCHECKED_CAST")

package com.example.composablefunctest.ChangeImage

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.R
import com.example.composablefunctest.Route
import com.example.composablefunctest.common.CustomTopAppBar
import com.example.composablefunctest.ui.theme.imageCircleIconColor
import kotlinx.coroutines.launch

@Composable
fun ChangeImageScreen(
    navController: NavController,
    viewModel: ChangeImageViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val cameraPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { }
    val galleryPicker = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { }
    val modalBottomState = rememberModalBottomSheetState()
    val coroutineScore = rememberCoroutineScope()

    LaunchedEffect(state.isModalBottomSheetOpen) {
        if (!state.isModalBottomSheetOpen){
            modalBottomState.hide()
        }
    }

    val modalBottomContent = listOf(
        listOf(
            ImageVector.vectorResource(R.drawable.camera_icon),
            {
                galleryPicker.launch(null)
            },
            stringResource(R.string.take_a_photo)
        ),
        listOf(
            ImageVector.vectorResource(R.drawable.gallery_icon),
            {
                cameraPicker.launch("image/*")
            },
            stringResource(R.string.pick_image_from_gallery)
        ),
    )


    Column {
        CustomTopAppBar(
            title = stringResource(R.string.pick_image),
            icon = Icons.AutoMirrored.Default.KeyboardArrowLeft
        ) {
            navController.popBackStack()
        }

        Spacer(Modifier.height(30.dp))

        AnimatedVisibility(state.isModalBottomSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.onEvent(ChangeImageEvent.ChangeBottomSheetState)
                },
                sheetState = modalBottomState,
                containerColor = MaterialTheme.colorScheme.primary.copy(
                    red = MaterialTheme.colorScheme.primary.red - 30f
                ),
                contentWindowInsets = { WindowInsets(0, 0, 0, 0) },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    modalBottomContent.forEach {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = null,
                                    indication = ripple()
                                ) {
                                    (it[1] as () -> Unit).invoke()
                                }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(imageCircleIconColor, CircleShape),
                                contentAlignment = Alignment.Center
                            ){
                                Icon(
                                    imageVector = it[0] as ImageVector,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .fillMaxSize()
                                )
                            }
                            Spacer(Modifier.height(10.dp))

                            Text(
                                text = it[2] as String,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .size(55.dp),
                                overflow = TextOverflow.Visible
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                    }
                }

                Spacer(Modifier.height(30.dp))
            }
        }
    }
}