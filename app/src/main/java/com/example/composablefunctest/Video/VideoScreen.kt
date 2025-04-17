package com.example.composablefunctest.Video

import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.composablefunctest.R
import com.example.composablefunctest.common.CustomAlertDialog
import com.example.composablefunctest.common.CustomTopAppBar

@OptIn(UnstableApi::class)
@Composable
fun VideoScreen(
    navController: NavController,
    viewModel: VideoViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val context = LocalContext.current

    val player = if (state.videoTime != null){
        remember(context) {
            ExoPlayer.Builder(context)
                .build().apply {
                    setMediaItem(
                        MediaItem
                            .fromUri(
                                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                            )
                    )
                    prepare()
                    addListener(object : Player.Listener {
                        override fun onEvents(player: Player, events: Player.Events) {
                            super.onEvents(player, events)

                            viewModel.onEvent(VideoEvent.SetVideoTime(player.currentPosition))
                        }
                    })
                    seekTo(state.videoTime)
                }
        }
    }else{
        null
    }

    if (state.exception.isNotEmpty()){
        CustomAlertDialog(state.exception) {
            viewModel.onEvent(VideoEvent.ResetException)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        CustomTopAppBar(
            icon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
            title = stringResource(R.string.video_screen)
        ) {
            navController.popBackStack()
        }

        Spacer(Modifier.height(30.dp))

        if (player != null){
            AndroidView(
                factory = {
                    PlayerView(it).apply {
                        this.player = player
                        this.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height((LocalConfiguration.current.screenHeightDp / 3).dp)
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }

        DisposableEffect(Unit) {
            onDispose {
                Log.e("onDispose", "onDispose")
                player?.release()
            }
        }
    }
}