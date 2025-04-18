package com.example.composablefunctest.Main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composablefunctest.common.updateWidgetState
import com.example.domain.usecase.UserData.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO){
            updateWidgets()
        }
    }

    private suspend fun updateWidgets() {
        val name = getUserDataUseCase()?.userName ?: "Unknown user..."

        updateWidgetState(context, name)
    }
}