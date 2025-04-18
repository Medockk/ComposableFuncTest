package com.example.composablefunctest.ChangeName

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composablefunctest.common.updateWidgetState
import com.example.domain.usecase.UserData.ChangeUserNameUseCase
import com.example.domain.usecase.UserData.GetUserDataUseCase
import com.example.domain.usecase.utils.ConvertImageType
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChangeNameViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val changeUserNameUseCase: ChangeUserNameUseCase,
    private val convertImageType: ConvertImageType,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = mutableStateOf(ChangeNameState())
    val state: State<ChangeNameState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUserData()
        }
    }

    private suspend fun getUserData() {
        val userData = getUserDataUseCase()

        withContext(Dispatchers.Main) {
            _state.value = state.value.copy(
                userImage = if (userData?.userImage != null){
                    convertImageType.invoke(userData.userImage!!)
                }else{
                    null
                },
                userName = userData?.userName ?: ""
            )
        }
    }

    fun onEvent(event: ChangeNameEvent) {
        when (event) {
            ChangeNameEvent.SaveChanges -> {
                if (!_state.value.isChangeSaved) {
                    viewModelScope.launch(Dispatchers.IO) {
                        try {
                            changeUserNameUseCase(_state.value.userName)
                            _state.value = state.value.copy(
                                isSuccessfulSavingChanges = true
                            )
                        } catch (e: Exception) {
                            _state.value = state.value.copy(
                                exception = e.message ?: "Unknown error..."
                            )
                        }
                    }

                    viewModelScope.launch(Dispatchers.IO) {

                        updateWidgetState(context, _state.value.userName)
                    }
                }
            }

            is ChangeNameEvent.EnterUserName -> {
                _state.value = state.value.copy(
                    userName = event.value,
                    isChangeSaved = false
                )
            }

            is ChangeNameEvent.SetException -> {
                _state.value = state.value.copy(
                    exception = event.ex
                )
            }
        }
    }
}