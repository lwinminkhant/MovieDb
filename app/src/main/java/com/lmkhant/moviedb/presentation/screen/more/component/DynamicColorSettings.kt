package com.lmkhant.moviedb.presentation.screen.more.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lmkhant.moviedb.R
import com.lmkhant.moviedb.presentation.screen.more.settings.SettingScreenEvent
import com.lmkhant.moviedb.presentation.screen.more.settings.SettingScreenState
import com.lmkhant.moviedb.ui.theme.MovieDbTheme
import com.lmkhant.moviedb.ui.theme.spacing

@Composable
fun DynamicColorSettings(
    state: SettingScreenState,
    onEvent: (SettingScreenEvent) -> Unit
) {
    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clickable(onClick = {
                    onEvent(SettingScreenEvent.UpdateDynamicThemePreferences(!state.dynamicThemePreferences))
                })
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = spacing.large,
                        vertical = spacing.default
                    )
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(
                    space = spacing.extraSmall
                ), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.theme_color) + ": ",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text =
                    if (state.dynamicThemePreferences) {
                        stringResource(R.string.dynamic_color)
                    } else {
                        stringResource(R.string.static_color)
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = state.dynamicThemePreferences,
                    onCheckedChange = { newPreferences ->
                        onEvent(SettingScreenEvent.UpdateDynamicThemePreferences(newPreferences))
                    }
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewDynamicColorSettings() {
    MovieDbTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        Surface {
            DynamicColorSettings(state = SettingScreenState(), onEvent = {})
        }
    }
}

@Preview
@Composable
fun PreviewDynamicColorSettingsDarkMode() {
    MovieDbTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        Surface {
            DynamicColorSettings(state = SettingScreenState(), onEvent = {})
        }
    }
}
