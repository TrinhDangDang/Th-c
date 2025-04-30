package com.example.thuc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thuc.ui.theme.ThucTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    onAboutClick: () -> Unit,
    onDarkThemeClick: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        SettingItem(title = "Enable Notifications", onClick = { /* TODO: Add toggle later */ })

        SettingItem(
            title = "Dark Theme",
            switchEnabled = isDarkTheme,
            onCheckedChange = onDarkThemeClick
        )

        SettingItem(
            title = "About Thức",
            onClick = onAboutClick
        )
    }
}

@Composable
fun SettingItem(
    title: String,
    switchEnabled: Boolean? = null,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .height(56.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            if (switchEnabled != null && onCheckedChange != null) {
                Switch(
                    checked = switchEnabled,
                    onCheckedChange = onCheckedChange
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    ThucTheme {
        SettingScreen(
            isDarkTheme = true,
            onDarkThemeClick = {},
            onAboutClick = {}
        )
    }
}
