package com.example.composablefunctest.SendNotification

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composablefunctest.Main.MainActivity
import com.example.composablefunctest.R
import com.example.composablefunctest.common.checkPostNotification
import com.example.domain.usecase.Notification.SendNotificationAfterOneMinuteUseCase
import com.example.domain.usecase.Notification.SendNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendNotificationViewModel @Inject constructor(
    private val sendNotificationUseCase: SendNotificationUseCase,
    private val sendNotificationAfterOneMinuteUseCase: SendNotificationAfterOneMinuteUseCase,
    @ApplicationContext private val context: Context
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
                if (_state.value.activity != null){
                    if (checkPostNotification(_state.value.activity!!, context)){
                        viewModelScope.launch(Dispatchers.IO){
                            try {
                                sendNotificationUseCase(
                                    icon = R.drawable.notification_icon,
                                    title = _state.value.notificationTitle.ifBlank { "Title" },
                                    description = _state.value.notificationDescription.ifBlank { "Description" },
                                    intent = Intent(context, MainActivity::class.java)
                                )
                            } catch (e: Exception) {
                                Log.e("ex", e.message.toString())
                            }
                        }

                    }
                }
            }

            is SendNotificationEvent.SetActivity -> {
                _state.value = state.value.copy(
                    activity = event.value
                )
            }

            SendNotificationEvent.SendNotificationAfterOneMinuteClick -> {
                viewModelScope.launch(Dispatchers.IO){
                    try {
                        sendNotificationAfterOneMinuteUseCase(
                            title = _state.value.notificationTitle.ifBlank { "Title 1 minute" },
                            description = _state.value.notificationTitle.ifBlank { "Description 1 minute" },
                            icon = R.drawable.notification_icon,
                            intent = Intent()
                        )
                    } catch (e: Exception) {
                        Log.e("receiverEx", e.message.toString())
                    }
                }
            }
        }
    }
}