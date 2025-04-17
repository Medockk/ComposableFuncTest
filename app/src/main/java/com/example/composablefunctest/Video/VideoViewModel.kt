package com.example.composablefunctest.Video

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.UserData.GetVideoTimeUseCase
import com.example.domain.usecase.UserData.SetVideoTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getVideoTimeUseCase: GetVideoTimeUseCase,
    private val setVideoTimeUseCase: SetVideoTimeUseCase
) : ViewModel() {

    private val _state = mutableStateOf(VideoState())
    val state: State<VideoState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO){
            try {
                getVideoTime()
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    exception = e.message.toString()
                )
            }
        }
    }

    private suspend fun getVideoTime() {
        val time = getVideoTimeUseCase()

        withContext(Dispatchers.Main){
            _state.value = state.value.copy(
                videoTime = time
            )
        }
    }

    fun onEvent(event: VideoEvent){
        when (event){
            VideoEvent.ResetException -> {
                _state.value = state.value.copy(
                    exception = ""
                )
            }
            is VideoEvent.SetVideoTime -> {
                _state.value = state.value.copy(
                    videoTime = event.value
                )

                viewModelScope.launch(Dispatchers.IO){
                    try {
                        setVideoTimeUseCase(event.value)
                    } catch (e: Exception) {
                        _state.value = state.value.copy(
                            exception = e.message.toString()
                        )
                    }
                }
            }
        }
    }
}