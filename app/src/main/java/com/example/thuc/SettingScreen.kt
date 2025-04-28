package com.example.thuc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thuc.ui.theme.ThucTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TopAppBar(title = {Text(
            text = "Settings",
        )})



        SettingItem(title = "Enable Notifications", onClick = { /* TODO: Toggle notifications */ })
        SettingItem(title = "DarkTheme", onClick = { /* TODO: Open theme selector */ })
        //SettingItem(title = "Default Alarm Sound", onClick = { /* TODO: Choose alarm sound */ })
        SettingItem(title = "About Thức", onClick = { /* TODO: Show about dialog */ })
    }
}

@Composable
fun SettingItem(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    ThucTheme { // Wrap inside your app's theme for correct colors
        SettingScreen(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}