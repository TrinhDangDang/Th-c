package com.example.thuc

import android.os.Bundle
import android.media.RingtoneManager
import android.media.Ringtone
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.thuc.data.Quote
import com.example.thuc.ui.theme.ThucTheme
import kotlinx.coroutines.launch

class AlarmRingActivity : ComponentActivity() {
    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = (application as ThucApplication).container.togetherAIRepository
        val thucRepository = (application as ThucApplication).container.thucRepository

        ringtone = RingtoneManager.getRingtone(applicationContext, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        ringtone?.play()

        setContent {
            ThucTheme {
                var feeling by remember { mutableStateOf("") }
                var quote by remember { mutableStateOf<Quote?>(null) }
                var isLoading by remember { mutableStateOf(false) }
                val promptOptions = listOf(
                    "How do you feel?",
                    "Are you ready to start the day?",
                    "What would you like to do today?",
                    "What's on your mind?",
                    "How are you doing, really?",
                    "What's something you need to hear right now?"
                )
                val promptLabel = remember { promptOptions.random() }

                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text("⏰ Alarm Ringing!", style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = feeling,
                            onValueChange = { feeling = it },
                            label = { Text(promptLabel) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                isLoading = true
                                quote = null
                                // Use lifecycleScope for calling suspend function in activity
                                lifecycleScope.launch {
                                    val response = repository.getQuote(
                                        "I’m feeling $feeling. An alarm is ringing to wake me up. Give me a short inspirational quote. Format: \"Quote.\" — Author"
                                    )
                                    quote = response
                                    thucRepository.insertQuote(response)
                                    isLoading = false
                                }
                            },
                            enabled = feeling.isNotBlank()
                        ) {
                            Text("Send")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (isLoading) {
                            CircularProgressIndicator()
                        }

                        quote?.let {
                            Text("\"${it.text}\"", style = MaterialTheme.typography.bodyLarge)
                            Text("— ${it.author}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                        }

                        if (quote != null && !isLoading) {
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = {
                                    ringtone?.stop()
                                    finish()
                                }
                            ) {
                                Text("Dismiss")
                            }
                        }

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ringtone?.stop()
    }
}
