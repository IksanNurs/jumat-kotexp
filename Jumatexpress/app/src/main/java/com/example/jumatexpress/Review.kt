package com.example.jumatexpress

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Review") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFA726)
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFFFA726)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = { navController.navigate("home") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Laporan") },
                    label = { Text("Laporan") },
                    selected = false,
                    onClick = { navController.navigate("laporan") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Send, contentDescription = "Pengajuan") },
                    label = { Text("Pengajuan") },
                    selected = false,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LibraryBooks, contentDescription = "Logbook") },
                    label = { Text("Logbook") },
                    selected = false,
                    onClick = { navController.navigate("logbook") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profil") },
                    label = { Text("Profil") },
                    selected = false,
                    onClick = {}
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            item {
                // Review items
                ReviewItem(
                    "Dosen",
                    "30 Jan, 15:02",
                    "Laporan ini sudah cukup baik dan komprehensif, terutama pada bagian analisis dan solusi yang disusulkan. Namun, saya menyarankan untuk lebih memperjelas data pendukung di bagian metodologi agar pembaca dapat memahami proses yang dilakukan dengan lebih detail"
                )
                
                ReviewItem(
                    "Dosen",
                    "30 Jan, 15:05",
                    "Beberapa bagian seperti kesimpulan, perlu diperbaiki agar lebih merefleksikan hasil dan pembelajaran yang diperoleh selama praktek kerja"
                )
            }
        }
    }
}

@Composable
private fun ReviewItem(sender: String, date: String, message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = sender,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

