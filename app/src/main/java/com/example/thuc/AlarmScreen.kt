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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.thuc.data.Alarm
import com.example.thuc.data.Screen
import com.example.thuc.ui.theme.ThucTheme

@Composable
fun AlarmScreen(modifier: Modifier = Modifier, onAlarmClick: (Alarm) -> Unit, uiState: UiState){
//    val sampleAlarms = listOf(
//        Alarm(id = 1, time = "07:00 AM", label = "Morning Workout", daysOfWeek = "MO,TU,WE,TH,FR"),
//        Alarm(id = 2, time = "08:30 AM", label = "Work Alarm", daysOfWeek = "MO,TU,WE,TH,FR"),
//        Alarm(id = 3, time = "09:00 AM", label = "Weekend Brunch", daysOfWeek = "SA,SU"),
//        Alarm(id = 4, time = "06:00 AM", label = "Early Run", daysOfWeek = "WE,FR"),
//        Alarm(id = 5, time = "10:00 PM", label = "Go to Bed", daysOfWeek = "MO,TU,WE,TH,FR,SA,SU"),
//        Alarm(id = 6, time = "12:00 PM", label = "Lunch Break", daysOfWeek = "MO,TU,WE,TH,FR"),
//    )
    val alarms = uiState.alarms
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),

    ) {
        HeaderContent()


        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            items(items = alarms) { alarm ->
                AlarmItem(alarm = alarm, onAlarmClick = onAlarmClick)
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
fun AlarmItem(alarm: Alarm, modifier: Modifier = Modifier, onAlarmClick: (Alarm) -> Unit) {
    Card(
        onClick = {
            onAlarmClick(alarm)
            //navController.navigate(Screen.AlarmDetail.route)
                  },
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
            Column {
                Text(
                    text = alarm.label,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(alarm.time)
            }


            Switch(
                checked = alarm.isEnabled,
                onCheckedChange = {  }
            )
        }
    }
}

@Preview
@Composable
fun AlarmScreenPreview(){
    ThucTheme {
        AlarmScreen(
            uiState = UiState(
                alarms = listOf(
                    Alarm(time = "07:00 AM", label = "Workout", daysOfWeek = "MO,TU"),
                    Alarm(time = "08:30 AM", label = "Work", daysOfWeek = "MO,TU,WE")
                )
            ),
            onAlarmClick = {}
        )
    }
}