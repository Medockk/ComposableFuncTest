package com.example.composablefunctest.SendNotification

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composablefunctest.R
import com.example.domain.usecase.Notification.SendNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendNotificationViewModel @Inject constructor(
    private val sendNotificationUseCase: SendNotificationUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SendNotificationState())
    val state: State<SendNotificationState> = _state

    fun onEvent(event: SendNotificationEvent){
        when (event){
            is SendNotificationEvent.EnterDescription -> {
                _state.value = state.value.copy(
                    notificationDescription = event.value
                )
            }
            is SendNotificationEvent.EnterTitle -> {
                _state.value = state.value.copy(
                    notificationTitle = event.value
                )
            }
            SendNotificationEvent.SendNotificationClick -> {
                viewModelScope.launch(Dispatchers.IO){
                    try {
                        sendNotificationUseCase(
                            icon = R.drawable.notification_icon,
                            title = _state.value.notificationTitle.ifBlank { "Title" },
                            description = _state.value.notificationDescription.ifBlank { "Description" }
                        )
                    } catch (e: Exception) {
                        Log.e("ex", e.message.toString())
                    }
                }
            }
        }
    }
}