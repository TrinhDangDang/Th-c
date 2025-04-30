package com.example.thuc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.thuc.data.Alarm
import com.example.thuc.data.Screen
import com.example.thuc.data.ScreenType

@Composable
fun ThucNavigation(
    currentScreen: ScreenType,
    navController: NavHostController,
    thucViewModel: ThucViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = modifier
    ) {
        composable(route = Screen.Main.route) {
            ThucMainScreen(
                //thucViewModel = thucViewModel,
                uiState = thucViewModel.uiState.collectAsState().value,
                currentScreen = currentScreen,
                onAlarmClick = { alarm ->
                    thucViewModel.selectAlarm(alarm)
                    navController.navigate("${Screen.AlarmDetail.route}/${alarm.label}")
                },
                    //alarmLabel -> navController.navigate("${Screen.AlarmDetail.route}/${alarmLabel}")
                onAboutClick = {
                    navController.navigate(Screen.AboutThuc.route)
                }
            )
        }
        val alarmRouteArgument = "alarmRoute"
        composable(
            route = Screen.AlarmDetail.route + "/{$alarmRouteArgument}",
            arguments = listOf(navArgument(alarmRouteArgument) { type = NavType.StringType })
            ) {backStackEntry ->
           // val alarm by thucViewModel.getAlarmByLabel(alarmName).collectAsState(Alarm(label = "Default", time = "00", daysOfWeek = "MO,WE"))
            AlarmDetailScreen(
                onBackClick = {navController.popBackStack()},
                uiState = thucViewModel.uiState.collectAsState().value,
                alarmLabel = backStackEntry.arguments?.getString(alarmRouteArgument) ?: "Unknown",
                onSaveAlarm = {
                    alarm ->
                    if (alarm.id == 0) {
                        thucViewModel.insertAlarm(alarm)
                    } else {
                        thucViewModel.updateAlarm(alarm)
                    }
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.AboutThuc.route) {
            AboutScreen(onBackClick = {navController.popBackStack()})
        }

    }
}
