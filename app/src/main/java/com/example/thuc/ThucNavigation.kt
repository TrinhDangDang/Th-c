package com.example.thuc

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.thuc.data.Screen
import com.example.thuc.data.ScreenType

@Composable
fun ThucNavigation(
    currentScreen: ScreenType,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = modifier
    ) {
        composable(route = Screen.Main.route) {
            ThucMainScreen(
                currentScreen = currentScreen,
                navController = navController
            )
        }
        composable(route = Screen.AlarmDetail.route) {
            AlarmDetailScreen(navController = navController)
        }

        composable(route = Screen.AboutThuc.route) {
            AboutScreen(navController = navController)
        }

    }
}
