package com.lmkhant.moviedb.presentation.screen.more.settings

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lmkhant.moviedb.presentation.screen.more.component.DarkModeSettings
import com.lmkhant.moviedb.presentation.screen.more.component.DynamicColorSettings
import com.lmkhant.moviedb.ui.theme.MovieDbTheme
import com.lmkhant.moviedb.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    state: SettingScreenState,
    onEvent: (SettingScreenEvent) -> Unit,
    navController: NavHostController,
) {

    Surface {

        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = spacing.medium
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.large)
        ) {
            Spacer(
                modifier = Modifier.height(
                    height = spacing.medium
                )
            )

            DarkModeSettings(
                isOpened = state.isDarkModePreferencesMenuOpened,
                selectedTheme = state.darkModePreferences,
                onEvent = { onEvent(SettingScreenEvent.UpdateDarkModePreferences(it)) },
                onStateChanged = { onEvent(SettingScreenEvent.DarkModePreferencesMenuStateChanged) }
            )
            if (Build.VERSION.SDK_INT >= 31) {
                DynamicColorSettings(state, onEvent)
            }
        }


    }
}


@Preview(
    showBackground = true,
    name = "Setting Screen Day Mode",
    group = "Setting Screen"
)
@Composable
fun PreviewSettingScreen() {
    MovieDbTheme(
        dynamicColor = false
    ) {
        SettingScreen(
            state = SettingScreenState(),
            onEvent = {},
            navController = rememberNavController()
        )
    }
}

@Preview(
    showBackground = true,
    name = "Setting Screen Dark Mode",
    group = "Setting Screen"
)
@Composable
fun PreviewSettingScreenDarkMode() {
    MovieDbTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        SettingScreen(
            state = SettingScreenState(),
            onEvent = {},
            navController = rememberNavController()
        )
    }

}
