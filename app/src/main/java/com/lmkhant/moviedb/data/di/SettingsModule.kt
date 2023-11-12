package com.lmkhant.moviedb.data.di

import android.content.Context
import com.lmkhant.moviedb.data.setting.IUserPreferencesService
import com.lmkhant.moviedb.data.setting.UserPreferencesService
import com.lmkhant.moviedb.domain.repository.setting.IUserPreferencesRepository
import com.lmkhant.moviedb.domain.repository.setting.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun providePreferenceSettingsService(@ApplicationContext context: Context): IUserPreferencesService {
        return UserPreferencesService(context = context)
    }

    @Provides
    @Singleton
    fun providePreferenceSettingsRepository(userPreferencesService: IUserPreferencesService): IUserPreferencesRepository {
        return UserPreferencesRepository(userPreferencesService)
    }
}