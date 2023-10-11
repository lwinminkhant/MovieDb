package com.lmkhant.moviedb.data.setting

import kotlinx.coroutines.flow.Flow

interface IUserPreferencesService {
    suspend fun getUserTheme(): Flow<Int>
    suspend fun updateUserTheme(themePreferences: Int)
    suspend fun getUserPreferenceOnDynamicTheme(): Flow<Boolean>
    suspend fun updateUserPreferenceOnDynamicTheme(dynamicPreferences: Boolean)
}
