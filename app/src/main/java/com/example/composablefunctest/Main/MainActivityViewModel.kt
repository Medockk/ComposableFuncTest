package com.example.composablefunctest.Main

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composablefunctest.common.updateWidgetState
import com.example.domain.model.NetworkResult
import com.example.domain.usecase.Configuration.IsApplicationInDarkThemeUseCase
import com.example.domain.usecase.Configuration.SetApplicationThemeUseCase
import com.example.domain.usecase.UserData.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getApplicationThemeUseCase: IsApplicationInDarkThemeUseCase,
    @ApplicationContext private val context: Context,
    private val setApplicationThemeUseCase: SetApplicationThemeUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MainActivityState())
    val state: State<MainActivityState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getAppTheme()
                updateWidgets()
            } catch (_: Exception) {

            }
        }
    }

    private suspend fun getAppTheme() {

        getApplicationThemeUseCase().collect {
            when (it) {
                is NetworkResult.Error<*> -> {}
                is NetworkResult.Loading<*> -> {}
                is NetworkResult.Success<*> -> {
                    withContext(Dispatchers.Main) {
                        _state.value = state.value.copy(
                            appTheme = it.data
                        )
                    }
                }
            }
        }
    }

    private suspend fun updateWidgets() {
        val name = getUserDataUseCase()?.userName ?: "Unknown user..."

        updateWidgetState(context, name)
    }

    fun setAppTheme(theme: Boolean?) {
        _state.value = state.value.copy(
            appTheme = theme
        )
    }
}