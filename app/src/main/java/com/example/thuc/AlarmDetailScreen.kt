package com.example.thuc

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thuc.ui.theme.ThucTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmDetailScreen() {
    var selectedDays by remember { mutableStateOf(listOf<String>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Alarm", style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Handle back click */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TimePickerDemo()

            OutlinedTextField(
                value = "Wake up",
                onValueChange = {},
                label = { Text("Label") },
                modifier = Modifier.fillMaxWidth()
            )

            DaySelectionRow(
                selectedDays = selectedDays,
                onDayToggle = { day ->
                    selectedDays = if (day in selectedDays) {
                        selectedDays - day
                    } else {
                        selectedDays + day
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* TODO: Save alarm changes */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
fun TimePickerDemo() {
    var timeText by remember { mutableStateOf("07:30 AM") }

    val context = LocalContext.current
    OutlinedTextField(
        value = timeText,
        onValueChange = {}, // No manual editing
        label = { Text("Time") },
        modifier = Modifier.fillMaxWidth(),
        readOnly = true, // Important: make field read-only
        enabled = true,
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = { showTimePicker(context) { newTime -> timeText = newTime } }) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Pick Time"
                )
            }

        }
    )
}

// ✅ Move this OUTSIDE too
fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            val amPm = if (selectedHour >= 12) "PM" else "AM"
            val hourFormatted = if (selectedHour % 12 == 0) 12 else selectedHour % 12
            val formattedTime = String.format("%02d:%02d %s", hourFormatted, selectedMinute, amPm)
            onTimeSelected(formattedTime)
        },
        hour,
        minute,
        false // 12-hour format
    ).show()
}

@Composable
fun DaySelectionRow(
    selectedDays: List<String>,
    onDayToggle: (String) -> Unit
) {
    val allDays = listOf("MO", "TU", "WE", "TH", "FR", "SA", "SU")
    Column {
        Text("Days of week")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            allDays.forEach { day ->
                val isSelected = selectedDays.contains(day)

                FilterChip(
                    selected = isSelected,
                    onClick = { onDayToggle(day) },
                    label = { Text(day) }
                )
            }
        }
    }


}
@Preview(showBackground = true)
@Composable
fun AlarmDetailPreview() {
    ThucTheme {
        AlarmDetailScreen()
    }
}
