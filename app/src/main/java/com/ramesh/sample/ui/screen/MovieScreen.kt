package com.ramesh.sample.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramesh.sample.ui.viewmodel.MovieViewModel

@Composable
fun MovieScreen(viewModel: MovieViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsState()
    var searchTerm by remember { mutableStateOf("iron man") }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchTerm,
            onValueChange = { searchTerm = it },
            label = { Text("Search") },
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = { viewModel.onSearch(searchTerm) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Search")
        }
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(uiState.movies) { movie ->
                Text(text = movie.title ?: "")
            }
        }
    }
}
