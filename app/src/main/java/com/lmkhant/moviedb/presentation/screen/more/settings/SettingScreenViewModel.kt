package com.lmkhant.moviedb.presentation.screen.more.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmkhant.moviedb.domain.repository.setting.IUserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(
    private val useCases: IUserPreferencesRepository
): ViewModel() {
    val settingScreenState = MutableStateFlow(SettingScreenState())

    fun onEvent(event: SettingScreenEvent) {
        when (event) {


            SettingScreenEvent.DarkModePreferencesMenuStateChanged -> settingScreenState.update {
                it.copy(
                    isDarkModePreferencesMenuOpened = !it.isDarkModePreferencesMenuOpened
                )
            }

            is SettingScreenEvent.UpdateDarkModePreferences -> viewModelScope.launch {
                useCases.updateUserTheme(
                    themePreferences = event.darkModePreferences
                )
            }

            is SettingScreenEvent.UpdateDynamicThemePreferences -> viewModelScope.launch {
                useCases.updateUserPreferenceOnDynamicTheme(
                    dynamicPreferences = event.dynamicThemePreferences
                )
            }
        }
    }

    init {
        viewModelScope.launch {
            useCases.getUserTheme().collectLatest { theme ->
                settingScreenState.update {
                    it.copy(
                        darkModePreferences = theme
                    )
                }
            }
        }

        viewModelScope.launch {
            useCases.getUserPreferenceOnDynamicTheme().collectLatest { dynamicTheme ->
                settingScreenState.update {
                    it.copy(
                        dynamicThemePreferences = dynamicTheme
                    )
                }
            }
        }
    }
}