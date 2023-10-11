package com.lmkhant.moviedb.presentation.screen.more.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lmkhant.moviedb.R
import com.lmkhant.moviedb.ui.theme.MovieDbTheme
import com.lmkhant.moviedb.ui.theme.spacing

@Composable
fun DarkModeSettings(
    isOpened: Boolean,
    selectedTheme: Int,
    onEvent: (Int) -> Unit,
    onStateChanged: () -> Unit
) {

    if (isOpened) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.theme))
            Card {
                Box(modifier = Modifier) {
                    Column {
                        SettingMenuItem(title = stringResource(id = R.string.system)) {
                            onEvent(3)
                            onStateChanged.invoke()
                        }

                        SettingMenuItem(title = stringResource(id = R.string.light)) {
                            onEvent(1)
                            onStateChanged.invoke()
                        }
                        SettingMenuItem(title = stringResource(id = R.string.dark)) {
                            onEvent(2)
                            onStateChanged.invoke()
                        }
                    }
                    IconButton(
                        onClick = { onStateChanged.invoke() },
                        modifier = Modifier.align(alignment = Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = stringResource(id = R.string.theme),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    } else {
        Row(
            modifier = Modifier.clickable(
                onClick = { onStateChanged.invoke() }
            ),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Card(Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(start = spacing.large),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.theme) + ": ",
                        style = MaterialTheme.typography.titleMedium,

                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = when (selectedTheme) {
                            1 -> stringResource(R.string.light)
                            2 -> stringResource(R.string.dark)
                            else -> stringResource(R.string.system)
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { onStateChanged.invoke() }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = stringResource(id = R.string.theme),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun SettingMenuItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clickable(onClick = {
                onClick()
            }), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title, Modifier.padding(horizontal = spacing.large)
        )
    }
}

@Preview
@Composable
fun SettingMenuItemPreview() {
    SettingMenuItem(title = "Dark", {})
}

@Preview(showBackground = true)
@Composable
fun PreviewDarkModeSettings() {
    MovieDbTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        Surface {
            DarkModeSettings(
                isOpened = false,
                selectedTheme = 1,
                onEvent = {},
                onStateChanged = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDarkModeSettingsDarkMode() {
    MovieDbTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        Surface {
            DarkModeSettings(
                isOpened = false,
                selectedTheme = 0,
                onEvent = {},
                onStateChanged = {}
            )
        }
    }
}
