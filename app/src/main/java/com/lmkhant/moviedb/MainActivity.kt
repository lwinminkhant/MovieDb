package com.lmkhant.moviedb

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import com.lmkhant.moviedb.data.network.NetworkApi
import com.lmkhant.moviedb.presentation.MoviesApp
import com.lmkhant.moviedb.presentation.screen.more.settings.SettingScreenState
import com.lmkhant.moviedb.presentation.screen.more.settings.SettingScreenViewModel
import com.lmkhant.moviedb.ui.theme.MovieDbTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var apiInterface: NetworkApi
    private val settingScreenViewModel: SettingScreenViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setContent {
            val themeState = settingScreenViewModel.settingScreenState
                .collectAsState(initial = SettingScreenState())

            val theme  = themeState.value

            MovieDbTheme(
                darkTheme = when (theme.darkModePreferences) {
                    1 -> false
                    2 -> true
                    else -> isSystemInDarkTheme()
                },
                dynamicColor = theme.dynamicThemePreferences
            ) {
                // A surface container using the 'background' color from the theme
                Surface{
                    MoviesApp()
                }
            }
        }
    }
}