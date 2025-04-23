package com.example.composablefunctest.Carousel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.UserData.GetCarouselItemUseCase
import com.example.domain.usecase.UserData.SetCarouselItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CarouselViewModel @Inject constructor(
    private val getCarouselItemUseCase: GetCarouselItemUseCase,
    private val setCarouselItemUseCase: SetCarouselItemUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CarouselState())
    val state: State<CarouselState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getCarouselItem()
            } catch (_: Exception) {

            }
        }
    }

    private suspend fun getCarouselItem() {
        val data = getCarouselItemUseCase()
        Log.e("data", data.toString())

        withContext(Dispatchers.Main) {
            _state.value = state.value.copy(
                currentItem = data
            )
        }
    }

    fun onEvent(event: CarouselEvent) {
        when (event) {
            is CarouselEvent.SetCarouselItem -> {
                if (
                    event.value < _state.value.items.size
                    && event.value >= 0
                ) {
                    Log.e("if", event.value.toString())
                    viewModelScope.launch(Dispatchers.IO) {
                        try {
                            setCarouselItemUseCase(event.value)
                        } catch (_: Exception) {

                        }
                    }
                }
            }
        }
    }
}