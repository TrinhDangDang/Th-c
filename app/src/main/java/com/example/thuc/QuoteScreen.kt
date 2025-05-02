package com.example.thuc

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.thuc.data.Quote
import androidx.compose.material.icons.filled.Shuffle



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onQuoteButtonClick: (String) -> Unit,
    onQuoteDelete: (Quote) -> Unit
) {
    var selectedQuoteForDeletion by remember { mutableStateOf<Quote?>(null) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        uiState.quote?.let { latest ->
            LatestQuoteCard(quote = latest)
        }
        Quotes(
            quotes = uiState.quotes,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            onQuoteLongPress = { selectedQuoteForDeletion = it }
        )
        if (uiState.isLoading) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Fetching something inspiring…")
            }

        FeelingButtons(onQuoteButtonClick = onQuoteButtonClick)
    }
    if (selectedQuoteForDeletion != null) {
        AlertDialog(
            onDismissRequest = { selectedQuoteForDeletion = null },
            confirmButton = {
                Button(onClick = {
                    onQuoteDelete(selectedQuoteForDeletion!!)
                    selectedQuoteForDeletion = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { selectedQuoteForDeletion = null }) {
                    Text("Cancel")
                }
            },
            title = { Text("Delete Quote") },
            text = { Text("Are you sure you want to delete this quote?") }
        )
    }
}


@Composable
fun FeelingButtons(onQuoteButtonClick: (String) -> Unit) {
    var shuffledFeelings by remember {
        mutableStateOf(defaultFeelings.shuffled())
    }

    Column {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            items(shuffledFeelings.take(3)) { feeling ->
                Button(onClick = { onQuoteButtonClick(feeling) }) {
                    Text(feeling, maxLines = 1)
                }
            }
            item {
                IconButton(
                    onClick = {
                        shuffledFeelings = defaultFeelings.shuffled()
                    },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .height(48.dp) // match button height
                ) {
                    Icon(
                        imageVector = Icons.Default.Shuffle, // You can replace with Icons.Default.Shuffle if added
                        contentDescription = "Shuffle Feelings",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun Quotes(modifier: Modifier = Modifier, quotes: List<Quote>, onQuoteLongPress: (Quote) -> Unit){
    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        items (quotes){
            quote ->
            QuoteItem(quote, onLongPress = onQuoteLongPress)
        }
    }

}

@Composable
fun QuoteItem(quote: Quote, modifier: Modifier = Modifier, onLongPress: (Quote) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = { onLongPress(quote) })
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "\"${quote.text}\"",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
            )
            Text(
                text = quote.author,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LatestQuoteCard(quote: Quote) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "\"${quote.text}\"",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = quote.author,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

//@Preview
//@Composable
//fun QuoteScreenPreview(){
//    QuoteScreen(modifier = Modifier.fillMaxWidth()
//        .padding(16.dp) )
//}

//data class Quote(
//    val quote: String,
//    val author: String
//)

private val defaultFeelings = listOf(
    // Negative / overwhelmed
    "I feel like giving up",
    "Life is really hard right now",
    "Everything feels hopeless",
    "I'm tired of trying",
    "I don't think I can keep going",
    "I'm lost",
    "I feel empty",
    "I'm overwhelmed",
    "I can't focus",
    "I feel unmotivated",
    "Nothing matters anymore",
    "I'm stuck in a rut",
    "I'm mentally exhausted",
    "I feel broken",
    "Why does everything hurt?",
    "It’s hard to wake up",
    "I feel like a failure",
    "I’m drowning in responsibilities",
    "I'm anxious about everything",
    "I feel so alone",
    "Nothing ever goes right",
    "I'm emotionally drained",
    "I can’t catch a break",
    "I'm sick of pretending I'm okay",
    "Everything is too much right now",

    // Neutral / introspective
    "I want to feel better",
    "I need some hope",
    "I need encouragement",
    "I'm just trying to get through the day",
    "I'm looking for clarity",
    "I'm healing",
    "I'm figuring things out",

    // Positive / growth-minded
    "I'm learning to be kind to myself",
    "I want to grow",
    "I'm ready for a change",
    "I'm trying to stay positive",
    "I'm hopeful for the future",
    "I'm grateful for small wins",
    "I'm building resilience",
    "I'm embracing who I am",
    "I'm focusing on self-care",
    "I want to be inspired"
)
