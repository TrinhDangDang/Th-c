package com.example.thuc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.thuc.data.Alarm
import com.example.thuc.data.ScreenType

@Composable
fun ThucMainScreen(
    uiState: UiState,
    onAlarmClick: (Alarm) -> Unit,
    onAboutClick: () -> Unit,
    currentScreen: ScreenType,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onDarkThemeClick: (Boolean) -> Unit
) {
    when (currentScreen) {
        ScreenType.Alarm -> AlarmScreen(
            onAlarmClick = {alarm -> onAlarmClick(alarm)},
            uiState = uiState,
            modifier = modifier
                .fillMaxSize(),
        )
        ScreenType.Quote -> QuoteScreen(
            modifier = modifier
                .fillMaxSize()
        )
        ScreenType.Setting -> SettingScreen(
            modifier = modifier
                .fillMaxSize(),
            onDarkThemeClick = onDarkThemeClick,
            onAboutClick = onAboutClick,
            isDarkTheme = isDarkTheme
        )
    }
}