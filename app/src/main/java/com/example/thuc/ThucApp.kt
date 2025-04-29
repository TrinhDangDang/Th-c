package com.example.thuc

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.thuc.data.Screen
import com.example.thuc.data.ScreenType
import com.example.thuc.ui.theme.ThucTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThucApp() {
    var currentScreen: ScreenType by remember { mutableStateOf(ScreenType.Alarm) }
    val navController = rememberNavController()
    val navigationItemContentList = listOf(
        NavigationItemContent(
            screenType = ScreenType.Alarm,
            icon = Icons.Default.Notifications,
            text = "Alarm"
        ),
        NavigationItemContent(
            screenType = ScreenType.Quote,
            icon = Icons.Default.Star,
            text = "Quote"
        ),
        NavigationItemContent(
            screenType = ScreenType.Setting,
            icon = Icons.Default.Settings,
            text = "Setting"
        )
    )

    Scaffold (
//        topBar = {
//            TopAppBar(
//                title =
//                {
//                    Text(text = "Thức",)
//                },
//                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primaryContainer)
//            )
//        },
        bottomBar = {
            ThucAppBottomBar(
            navigationItemContentList = navigationItemContentList,
            onClick = {current -> currentScreen = current},
            currentScreenType = currentScreen
        )},
        floatingActionButton = {
            if (currentScreen == ScreenType.Alarm){
                FloatingActionButton(
                    onClick = {navController.navigate(Screen.AlarmDetail.route)}
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }
            }
        }
    ){ innerPadding ->
        ThucNavigation(
            currentScreen = currentScreen,
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )

    }
}



@Composable
fun ThucAppBottomBar(modifier: Modifier = Modifier, navigationItemContentList: List<NavigationItemContent>, onClick: (ScreenType) -> Unit, currentScreenType: ScreenType){
    NavigationBar(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentScreenType == navItem.screenType,
                onClick = {onClick(navItem.screenType)},
                icon = {Icon(imageVector = navItem.icon, contentDescription = navItem.text)},
                label = { Text(text = navItem.text) }
            )
        }
    }
}

data class NavigationItemContent(
    val screenType: ScreenType,
    val icon: ImageVector,
    val text: String
)

@Preview
@Composable
fun ThucAppPreview() {
    ThucTheme {
        ThucApp()
    }
}
