package com.example.thuc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AlarmScreen(modifier: Modifier = Modifier){
    val alarms = listOf("Alarm 1", "Alarm 2", "Alarm 3", "Alarm 4")
    Column(
        modifier = modifier
    ) {
        HeaderContent()

        // 2. Stuck MiniTopRow
        MiniTopRow()

        // 3. Fixed Alarms List
        Column(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .weight(1f) // fill remaining space
                .verticalScroll(rememberScrollState())
        ) {
            alarms.forEach { alarm ->
                AlarmItem(alarm)
            }
        }

    }
}

@Composable
fun HeaderContent() {
    Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "\"Today is a new beginning 🌞\"",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MiniTopRow() {
    Surface(
        //tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Thức",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
//            IconButton(onClick = { /* TODO: add new alarm */ }) {
//                Icon(Icons.Default.Add, contentDescription = "Add Alarm", tint = MaterialTheme.colorScheme.onPrimaryContainer)
//            }
        }
    }
}

@Composable
fun AlarmItem(alarm: String) {
    Text(
        text = alarm,
        style = MaterialTheme.typography.bodyLarge,
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}