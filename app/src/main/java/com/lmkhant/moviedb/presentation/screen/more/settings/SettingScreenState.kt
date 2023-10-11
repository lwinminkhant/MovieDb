package com.lmkhant.moviedb.presentation.screen.more.settings

data class SettingScreenState(
    val isDisplayStylePreferencesMenuOpened: Boolean = false,
    val isDarkModePreferencesMenuOpened: Boolean = false,
    val darkModePreferences: Int = 0,
    val isDynamicThemePreferencesMenuOpened: Boolean = false,
    val dynamicThemePreferences: Boolean = true
)
