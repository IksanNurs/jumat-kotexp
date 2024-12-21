package com.example.jumatexpress

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaporanScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "LAPORAN",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFFFA726)
                )
            )
        },
        // Menambahkan bottom navigation bar yang sama dengan HomeScreen
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFFFA726)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Laporan") },
                    label = { Text("Laporan") },
                    selected = true,
                    onClick = {
                        navController.navigate("laporan")
                    }
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
                    onClick = {
                        // Navigate to LogbookNavigation when pressed
                        navController.navigate("logbook")
                    }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Laporan Akhir Kerja Praktek",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Halaman ini memungkinkan mahasiswa untuk mengunggah laporan akhir kerja praktek yang telah diselesaikan, serta mengakses feedback atau review dari pembimbing. Mahasiswa juga dapat mengunggah revisi laporan berdasarkan komentar atau saran yang diberikan. Proses ini merupakan tahap penting dalam penyelesaian kerja praktek sebagai syarat penilaian akhir.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Card untuk Laporan Akhir
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFA726)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            Icons.Default.Description,
                            contentDescription = "Laporan",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Laporan Akhir",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                    
                    Text(
                        text = "Laporan Akhir Kerja Praktek",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    
                    Button(
                        onClick = {
                            navController.navigate("pengajuan")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            "Continue",
                            color = Color(0xFFFFA726)
                        )
                    }
                }
            }
        }
    }
}
