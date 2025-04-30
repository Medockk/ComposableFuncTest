package com.example.composablefunctest.Configuration

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.NetworkResult
import com.example.domain.usecase.Configuration.IsApplicationInDarkThemeUseCase
import com.example.domain.usecase.Configuration.SetApplicationThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConfigurationViewModel @Inject constructor(
    private val getApplicationThemeUseCase: IsApplicationInDarkThemeUseCase,
    private val setApplicationThemeUseCase: SetApplicationThemeUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = mutableStateOf(ConfigurationState())
    val state: State<ConfigurationState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO){
            try {
                getTheme()
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    exception = e.message.toString()
                )
            }
        }
    }

    private suspend fun getTheme() {
        val theme = getApplicationThemeUseCase.invoke()

        theme.collect {
            when (it){
                is NetworkResult.Error<*> -> {
                    _state.value = state.value.copy(
                        exception = it.message ?: "Unknown error..."
                    )
                }
                is NetworkResult.Loading<*> -> {
                    _state.value = state.value.copy(
                        showIndication = true
                    )
                }
                is NetworkResult.Success<*> -> {
                    withContext(Dispatchers.Main){
                        _state.value = state.value.copy(
                            appTheme = it.data,
                            showIndication = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: ConfigurationEvent){
        when (event){
            is ConfigurationEvent.SetTheme -> {
                viewModelScope.launch(Dispatchers.IO){
                    withContext(Dispatchers.Main){
                        _state.value = state.value.copy(
                            appTheme = event.theme
                        )
                    }
                    try {
                        setApplicationThemeUseCase.invoke(event.theme)
                        _state.value.themeClick.invoke(event.theme)
                    } catch (e: Exception) {
                        _state.value = state.value.copy(
                            exception = e.message.toString()
                        )
                    }
                }
            }

            is ConfigurationEvent.SetChangeThemeClick -> {
                _state.value = state.value.copy(
                    themeClick = event.onClick
                )
            }

            is ConfigurationEvent.SetLanguage -> {
                with(context){
                    _state.value = state.value.copy(
                        currentLanguage = when (event.value){
                            LanguageList.English -> {
                                setLocale("en")
                                "English"
                            }
                            LanguageList.Russian -> {
                                setLocale("ru")
                                "Русский"
                            }
                        }
                    )
                }
            }
        }
    }

    private fun Context.setLocale(localeTag: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(localeTag)
        }else{
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(localeTag)
            )
        }
    }
}