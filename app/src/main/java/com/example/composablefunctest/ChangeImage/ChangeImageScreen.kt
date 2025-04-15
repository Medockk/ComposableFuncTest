@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("UNCHECKED_CAST")

package com.example.composablefunctest.ChangeImage

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomAlertDialog
import com.example.composablefunctest.common.CustomButton
import com.example.composablefunctest.common.CustomTopAppBar
import com.example.composablefunctest.ui.theme.imageCircleIconColor

@Composable
fun ChangeImageScreen(
    navController: NavController,
    viewModel: ChangeImageViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val modalBottomState = rememberModalBottomSheetState()
    val context = LocalContext.current

    val cameraPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            val stream = context.contentResolver.openInputStream(it)
            viewModel.onEvent(ChangeImageEvent.PickCameraImage(stream?.readBytes()))
            stream?.close()
        }
    }
    val galleryPicker =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            viewModel.onEvent(ChangeImageEvent.PickGalleryImage(it))
        }

    LaunchedEffect(state.isModalBottomSheetOpen) {
        if (!state.isModalBottomSheetOpen) {
            modalBottomState.hide()
        } else {
            modalBottomState.expand()
        }
    }

    if (state.exception.isNotEmpty()){
        CustomAlertDialog(state.exception) {
            viewModel.onEvent(ChangeImageEvent.ResetException)
        }
    }

    val modalBottomContent = listOf(
        listOf(
            ImageVector.vectorResource(R.drawable.camera_icon),
            {
                galleryPicker.launch(null)
                viewModel.onEvent(ChangeImageEvent.ChangeBottomSheetState)
            },
            stringResource(R.string.take_a_photo)
        ),
        listOf(
            ImageVector.vectorResource(R.drawable.gallery_icon),
            {
                cameraPicker.launch("image/*")
                viewModel.onEvent(ChangeImageEvent.ChangeBottomSheetState)
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

        if (state.image != null) {
            Image(
                bitmap = state.image.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                    },
                contentScale = ContentScale.Crop
            )
        } else {
            Spacer(Modifier.weight(1f))
            CustomButton(
                text = stringResource(R.string.pick_your_photo),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(50.dp)
                    .padding(horizontal = 20.dp)
            ) {
                viewModel.onEvent(ChangeImageEvent.ChangeBottomSheetState)
            }
        }

        AnimatedVisibility(state.isModalBottomSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = {
                    viewModel.onEvent(ChangeImageEvent.ChangeBottomSheetState)
                },
                sheetState = modalBottomState,
                containerColor = MaterialTheme.colorScheme.primary,
                contentWindowInsets = { WindowInsets(0, 0, 0, 0) },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Spacer(Modifier.width(15.dp))
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
                            ) {
                                Icon(
                                    imageVector = it[0] as ImageVector,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .fillMaxSize()
                                )
                            }
                            Spacer(Modifier.height(5.dp))

                            Text(
                                text = it[2] as String,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 10.sp,
                                overflow = TextOverflow.Visible,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(Modifier.width(20.dp))
                    }
                    Spacer(Modifier.width(15.dp))
                }

                Spacer(Modifier.height((LocalConfiguration.current.screenHeightDp / 10).dp))
            }
        }
    }
}