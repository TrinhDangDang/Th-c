package com.example.thuc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(modifier: Modifier = Modifier){
    val quotes: List<Quote> = listOf(
        Quote(
            "Hello this is a sample quote",
            "M"
        ),
        Quote(
            "another sample quote",
            "B"
        )
    )
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Quotes(quotes = quotes, modifier = Modifier)
    }
}


@Composable
fun Quotes(modifier: Modifier = Modifier, quotes: List<Quote>){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        items (quotes){
            quote ->
            QuoteItem(quote)
        }
    }
}

@Composable
fun QuoteItem(quote: Quote, modifier: Modifier = Modifier){
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "\"${quote.quote}\"",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "- ${quote.author}",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun QuoteScreenPreview(){
    QuoteScreen(modifier = Modifier.fillMaxWidth()
        .padding(16.dp) )
}

data class Quote(
    val quote: String,
    val author: String
)


