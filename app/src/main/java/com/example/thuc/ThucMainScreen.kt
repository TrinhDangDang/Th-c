package com.example.thuc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.thuc.data.ScreenType

@Composable
fun ThucMainScreen(navController: NavController, currentScreen: ScreenType, modifier: Modifier = Modifier) {
    when (currentScreen) {
        ScreenType.Alarm -> AlarmScreen(
            modifier = modifier
                .fillMaxSize(),
            navController = navController
        )
        ScreenType.Quote -> QuoteScreen(
            modifier = modifier
                .fillMaxSize()
        )
        ScreenType.Setting -> SettingScreen(
            modifier = modifier
                .fillMaxSize(),
            navController = navController
        )
    }
}