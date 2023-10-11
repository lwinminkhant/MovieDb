package com.lmkhant.moviedb.presentation.screen.more.settings



sealed class SettingScreenEvent {
    data class UpdateDarkModePreferences(val darkModePreferences: Int) : SettingScreenEvent()
    object DarkModePreferencesMenuStateChanged : SettingScreenEvent()
    data class UpdateDynamicThemePreferences(val dynamicThemePreferences: Boolean) :
        SettingScreenEvent()
}
