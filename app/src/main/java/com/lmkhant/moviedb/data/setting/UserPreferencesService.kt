package com.lmkhant.moviedb.data.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesService(private val context: Context) : IUserPreferencesService {

    private val Context.preferences: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    private val themeKey: Preferences.Key<Int> =
        intPreferencesKey(THEME_PREFERENCES)
    private val dynamicThemeKey: Preferences.Key<Boolean> =
        booleanPreferencesKey(DYNAMIC_THEME_PREFERENCES)

    override suspend fun getUserTheme(): Flow<Int> =
        context.preferences.data.map {
            it[themeKey] ?: 0
        }
    val ss = context.preferences.data
    override suspend fun updateUserTheme(themePreferences: Int) {
        context.preferences.edit {
            it[themeKey] = themePreferences
        }
    }

    override suspend fun getUserPreferenceOnDynamicTheme(): Flow<Boolean> =
        context.preferences.data.map {
            it[dynamicThemeKey] ?: true
        }

    override suspend fun updateUserPreferenceOnDynamicTheme(dynamicPreferences: Boolean) {
        context.preferences.edit {
            it[dynamicThemeKey] = dynamicPreferences
        }
    }


    companion object {
        private const val PREFERENCES_NAME = "user_pref"
        private const val THEME_PREFERENCES = "user_theme"
        private const val DYNAMIC_THEME_PREFERENCES = "user_dynamic_theme"
    }
}
