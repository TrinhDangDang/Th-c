package com.example.thuc

import android.widget.Switch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.thuc.data.Screen
import com.example.thuc.ui.theme.ThucTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        SettingItem(title = "Enable Notifications", onClick = { /* TODO: Toggle notifications */ })
        SettingItem(title = "DarkTheme", onClick = { /* TODO: Open theme selector */ })
        //SettingItem(title = "Default Alarm Sound", onClick = { /* TODO: Choose alarm sound */ })
        SettingItem(title = "About Thức", onClick = { navController.navigate(route = Screen.AboutThuc.route)})
    }
}

@Composable
fun SettingItem(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .height(56.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(16.dp)
            )
            if(title == "DarkTheme"){
                Switch(
                    checked = false,
                    onCheckedChange = {  }
                )
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    ThucTheme { // Wrap inside your app's theme for correct colors
        SettingScreen(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            navController = rememberNavController()
        )
    }
}