package com.example.thuc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.thuc.data.Screen
import com.example.thuc.ui.theme.ThucTheme

@Composable
fun AlarmScreen(modifier: Modifier = Modifier, navController: NavController){
    val alarms = listOf("Alarm 1", "Alarm 2", "Alarm 3", "Alarm 4")
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),

    ) {
        HeaderContent()

        // 2. Stuck MiniTopRow
//        MiniTopRow()

        // 3. Fixed Alarms List
        Column(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .weight(1f) // fill remaining space
                .verticalScroll(rememberScrollState())
        ) {
            alarms.forEach { alarm ->
                AlarmItem(navController = navController, alarm)
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

//@Composable
//fun MiniTopRow() {
//    Surface(
//        //tonalElevation = 4.dp,
//        color = MaterialTheme.colorScheme.primaryContainer
//    ) {
//        Row(
//            modifier = androidx.compose.ui.Modifier
//                .fillMaxWidth()
//                .height(56.dp)
//                .padding(horizontal = 16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(
//                text = "Thức",
//                style = MaterialTheme.typography.titleLarge,
//                color = MaterialTheme.colorScheme.onPrimaryContainer
//            )
////            IconButton(onClick = { /* TODO: add new alarm */ }) {
////                Icon(Icons.Default.Add, contentDescription = "Add Alarm", tint = MaterialTheme.colorScheme.onPrimaryContainer)
////            }
//        }
//    }
//}

@Composable
fun AlarmItem(navController: NavController, alarm: String, modifier: Modifier = Modifier) {
    Card(
        onClick = {navController.navigate(Screen.AlarmDetail.route)},
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        //shape = RoundedCornerShape(topStart = 0.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()
            .height(56.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = alarm,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "date",
            )
            Switch(
                checked = true,
                onCheckedChange = {  }
            )
        }
    }
}

@Preview
@Composable
fun AlarmScreenPreview(){
    ThucTheme {
        AlarmScreen(navController = rememberNavController())
    }
}