package com.example.composablefunctest.Home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.UserData.GetUserDataUseCase
import com.example.domain.usecase.utils.ConvertImageType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val convertImageType: ConvertImageType
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state = _state as State<HomeState>

    init {
        viewModelScope.launch(Dispatchers.IO){
            getUserData()
        }
    }

    private suspend fun getUserData() {
        val userData = getUserDataUseCase()

        if (userData?.userImage != null){
            withContext(Dispatchers.Main){
                _state.value = state.value.copy(
                    userImage = convertImageType(userData.userImage!!),
                    userName = userData.userName
                )
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event){
            HomeEvent.ResetException -> {
                _state.value = state.value.copy(
                    exception = ""
                )
            }
        }
    }
}