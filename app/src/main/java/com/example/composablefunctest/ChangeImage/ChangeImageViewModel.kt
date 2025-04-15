package com.example.composablefunctest.ChangeImage

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.UserData.ChangeUserImageUseCase
import com.example.domain.usecase.utils.ConvertImageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeImageViewModel @Inject constructor(
    private val changeUserImageUseCase: ChangeUserImageUseCase,
    private val convertImageType: ConvertImageType
) : ViewModel() {

    private val _state = mutableStateOf(ChangeImageState())
    val state: State<ChangeImageState> = _state

    fun onEvent(event: ChangeImageEvent) {
        when (event) {
            ChangeImageEvent.ChangeBottomSheetState -> {
                _state.value = state.value.copy(
                    isModalBottomSheetOpen = !_state.value.isModalBottomSheetOpen
                )
            }

            is ChangeImageEvent.PickCameraImage -> {
                if (event.value != null) {

                    _state.value = state.value.copy(
                        image = convertImageType(event.value)
                    )

                    try {
                        updateUserImage(convertImageType(event.value))
                    } catch (e: Exception) {

                    }
                }

            }

            is ChangeImageEvent.PickGalleryImage -> {
                _state.value = state.value.copy(
                    image = event.value
                )

                try {
                    if (event.value != null) {
                        updateUserImage(event.value)
                    }
                } catch (e: Exception) {

                }
            }

            ChangeImageEvent.ResetException -> {
                _state.value = state.value.copy(
                    exception = ""
                )
            }
        }
    }

    private fun updateUserImage(bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            changeUserImageUseCase(convertImageType(bitmap))
        }
    }
}