package com.example.thuc

import android.R.attr.action
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.thuc.data.Alarm
import com.example.thuc.data.Quote
import com.example.thuc.data.ScreenType

@Composable
fun ThucMainScreen(
    uiState: UiState,
    onAlarmClick: (Alarm) -> Unit,
    onAboutClick: () -> Unit,
    currentScreen: ScreenType,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onDarkThemeClick: (Boolean) -> Unit,
    onCheckedChange: (Alarm) -> Unit,
    onQuoteButtonClick: (String) -> Unit,
    onQuoteDelete: (Quote) -> Unit
) {
    val context = LocalContext.current
    when (currentScreen) {
        ScreenType.Alarm -> AlarmScreen(
            onAlarmClick = {alarm -> onAlarmClick(alarm)},
            uiState = uiState,
            modifier = modifier
                .fillMaxSize(),
            onCheckedChange = onCheckedChange
        )
        ScreenType.Quote -> QuoteScreen(
            uiState = uiState,
            onQuoteButtonClick = {feeling -> onQuoteButtonClick(feeling)},
            onQuoteDelete = {quote -> onQuoteDelete(quote)},
            modifier = modifier
                .fillMaxSize()
        )
        ScreenType.Setting -> SettingScreen(
            modifier = modifier
                .fillMaxSize(),
            onDarkThemeClick = onDarkThemeClick,
            onAboutClick = onAboutClick,
            isDarkTheme = isDarkTheme,
            onNotificationClick = {
                val intent = Intent().apply {
                    action = android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
                context.startActivity(intent)
            }
        )
    }
}