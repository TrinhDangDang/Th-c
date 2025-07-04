package com.example.thuc

import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thuc.data.Alarm
import com.example.thuc.ui.theme.ThucTheme
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmDetailScreen(
    onBackClick: () -> Unit,
    alarmLabel: String,
    uiState: UiState,
    onSaveAlarm: (Alarm) -> Unit,
    onDeleteAlarm: (Alarm) -> Unit
) {
    val currentAlarm = if (alarmLabel == "default") {
        Alarm(time = "07:00 AM", label = "New Alarm", daysOfWeek = "")
    } else {
        uiState.currentSelectedAlarm ?: Alarm(time = "07:00 AM", label = "New Alarm", daysOfWeek = "")
    }

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var label by remember { mutableStateOf(currentAlarm.label) }
    var time by remember { mutableStateOf(currentAlarm.time) }
    var selectedDays by remember {
        mutableStateOf(currentAlarm.daysOfWeek.split(",").filter { it.isNotBlank() })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (currentAlarm.label == "New Alarm") "New Alarm" else "Edit Alarm",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
            TimePickerDemo(timeText = time, onTimeChange = { time = it })

            OutlinedTextField(
                value = label,
                onValueChange = { label = it },
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
            if (alarmLabel == "default") {
                // New alarm: Save button takes full width
                Button(
                    onClick = {
                        if (label.isBlank()) {
                            Toast.makeText(context, "Please enter a label", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        if (selectedDays.isEmpty()) {
                            Toast.makeText(context, "Please select at least one day", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        val updatedAlarm = Alarm(
                            id = currentAlarm.id,
                            time = time,
                            label = label,
                            daysOfWeek = selectedDays.joinToString(","),
                            isEnabled = currentAlarm.isEnabled
                        )
                        onSaveAlarm(updatedAlarm)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Save")
                }
            } else {
                // Editing: Show Save + Delete side-by-side
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            if (label.isBlank()) {
                                Toast.makeText(context, "Please enter a label", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            if (selectedDays.isEmpty()) {
                                Toast.makeText(context, "Please select at least one day", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            val updatedAlarm = Alarm(
                                id = currentAlarm.id,
                                time = time,
                                label = label,
                                daysOfWeek = selectedDays.joinToString(","),
                                isEnabled = currentAlarm.isEnabled
                            )
                            onSaveAlarm(updatedAlarm)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                    ) {
                        Text("Save")
                    }

                    TextButton(
                        onClick = { showDialog = true },
                        modifier = Modifier
                            .height(56.dp)
                            .align(Alignment.CenterVertically),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Delete")
                    }
                }
            }

        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Alarm") },
            text = { Text("Are you sure you want to delete this alarm?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteAlarm(currentAlarm)
                    onBackClick()
                    showDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun TimePickerDemo(timeText: String, onTimeChange: (String) -> Unit) {
    val context = LocalContext.current

    OutlinedTextField(
        value = timeText,
        onValueChange = {}, // still read-only
        label = { Text("Time") },
        modifier = Modifier.fillMaxWidth(),
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = {
                showTimePicker(context) { newTime -> onTimeChange(newTime) }
            }) {
                Icon(imageVector = Icons.Default.AccessTime, contentDescription = "Pick Time")
            }
        }
    )
}

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
        false
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
        Row(modifier = Modifier.fillMaxWidth()) {
            allDays.forEach { day ->
                val isSelected = day in selectedDays
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
        AlarmDetailScreen(
            onBackClick = {},
            alarmLabel = "default",
            uiState = UiState(),
            onSaveAlarm = {},
            onDeleteAlarm = {}
        )
    }
}
