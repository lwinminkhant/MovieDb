package com.lmkhant.moviedb.domain.repository.setting
import com.lmkhant.moviedb.data.setting.IUserPreferencesService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val userPreferencesService: IUserPreferencesService
) : IUserPreferencesRepository {

    override suspend fun getUserTheme(): Flow<Int> =
        userPreferencesService.getUserTheme()

    override suspend fun updateUserTheme(themePreferences: Int) {
        userPreferencesService.updateUserTheme(
            themePreferences = themePreferences
        )
    }

    override suspend fun getUserPreferenceOnDynamicTheme(): Flow<Boolean> =
        userPreferencesService.getUserPreferenceOnDynamicTheme()

    override suspend fun updateUserPreferenceOnDynamicTheme(dynamicPreferences: Boolean) {
        userPreferencesService.updateUserPreferenceOnDynamicTheme(
            dynamicPreferences = dynamicPreferences
        )
    }
}
