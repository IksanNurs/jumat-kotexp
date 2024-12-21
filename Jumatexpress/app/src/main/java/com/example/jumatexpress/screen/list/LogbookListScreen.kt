package com.example.jumatexpress.screen.list

import LogbookCard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jumatexpress.Logbook
import com.example.jumatexpress.MainViewModel
import com.example.jumatexpress.UiState

@Composable
fun LogbookScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    onItemClick: (Int) -> Unit
) {
    // Menggunakan collectAsState untuk StateFlow
    val logbooks by mainViewModel.logbooks.collectAsState()
    val uiState by mainViewModel.uiState.collectAsState()

    Column {
        when (uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is UiState.Success -> {
                LazyColumn {
                    items(logbooks) { logbook ->
                        // Mungkin menambahkan onItemClick untuk menangani klik
                        LogbookCard(logbook = logbook, onClick = { onItemClick(logbook.id) })
                    }
                }
            }
            is UiState.Error -> {
                val errorMessage = (uiState as UiState.Error).message
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            UiState.Successfull -> {
                // Handle case for Successfull if needed, else you can ignore or add another UI state
                Text("Success", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}



