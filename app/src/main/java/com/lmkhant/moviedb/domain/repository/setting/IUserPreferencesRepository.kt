package com.lmkhant.moviedb.domain.repository.setting

import kotlinx.coroutines.flow.Flow

interface IUserPreferencesRepository {
    suspend fun getUserTheme(): Flow<Int>
    suspend fun updateUserTheme(themePreferences: Int)
    suspend fun getUserPreferenceOnDynamicTheme(): Flow<Boolean>
    suspend fun updateUserPreferenceOnDynamicTheme(dynamicPreferences: Boolean)

}
