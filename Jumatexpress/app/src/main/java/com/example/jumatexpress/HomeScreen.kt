package com.example.jumatexpress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val steps = listOf(
        "1. Pembentukan Kelompok",
        "2. Penyusunan Proposal",
        "3. Pengajuan Proposal",
        "4. Penerbitan Surat Permohonan KP",
        "5. Pengiriman Surat ke Instansi",
        "6. Penyerahan Surat Balasan ke Instansi",
        "7. Penerbitan Surat Pengantar KP",
        "8. Pendaftaran Selesai"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Practical Work Management App",
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
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFFFA726)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Laporan") },
                    label = { Text("Laporan") },
                    selected = false,
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
            // Header Content
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Menjamin box mengisi seluruh lebar layar
                    .background(Color(0xFFFFA726)) // Background warna oranye
                    .padding(all = 16.dp) // Padding di semua sisi
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // Memastikan Column juga memenuhi lebar
                        .padding(horizontal = 8.dp) // Sedikit padding horizontal tambahan
                ) {
                    // Judul Utama
                    Text(
                        text = "Selamat Datang!",
                        modifier = Modifier.fillMaxWidth(), // Menjamin teks memenuhi lebar
                        textAlign = TextAlign.Start, // Rata kiri
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 28.sp // Ukuran font yang lebih besar dan tebal
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Spasi antara judul dan deskripsi

                    // Deskripsi
                    Text(
                        text = "Dari Pengajuan hingga Laporan, Semua dalam Genggaman Anda.",
                        modifier = Modifier.fillMaxWidth(), // Menjamin teks memenuhi lebar
                        textAlign = TextAlign.Start, // Rata kiri
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Black,
                            fontSize = 18.sp, // Ukuran font lebih besar dan nyaman dibaca
                            fontWeight = FontWeight.Light
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Steps Content
            Text(
                text = "Tata Cara Pendaftaran Kerja Praktek",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            steps.forEachIndexed { index, step ->
                Column {
                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    val additionalText = when (index) {
                        0 -> "Mahasiswa membentuk kelompok sesuai dengan ketentuan yang berlaku."
                        1 -> "Setiap kelompok menyusun proposal yang berisi profil instansi dan rencana kegiatan."
                        2 -> "Proposal diajukan kepada departemen terkait untuk mendapatkan persetujuan."
                        3 -> "Setelah disetujui, surat permohonan KP diterbitkan untuk instansi terkait."
                        4 -> "Surat permohonan KP dikirim ke instansi yang bersangkutan."
                        5 -> "Surat balasan dikembalikan ke departemen untuk proses lebih lanjut."
                        6 -> "Departemen menerbitkan surat pengantar KP untuk instansi."
                        7 -> "Setelah diterima oleh instansi, pendaftaran KP selesai tercatat."
                        else -> null
                    }
                    additionalText?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

// Tambahkan NavHost di composable utama yang mengelola navigasi
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("logbook") { LogbookNavigation(navController) } // Halaman Logbook
        
    }
}
