package com.example.jumatexpress


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import android.provider.OpenableColumns
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengajuanScreen(
    navController: NavController,
    viewModel: PengajuanViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var fileName by remember { mutableStateOf("") }
    
    // State untuk menyimpan item dari API
    var item by remember { mutableStateOf<Item?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // File picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            fileName = getFileName(context, it)
        }
    }

    // Load items saat pertama kali
    LaunchedEffect(Unit) {
        viewModel.getItems()
    }

    // Observe items state
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Laporan Akhir Kerja Praktek",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
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
                    selected = true,
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
                    onClick = { navController.navigate("profil") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Instruksi",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Silahkan Upload Laporan Akhir",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Text(
                text = "Laporan Akhir",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            // File Upload Section
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable { launcher.launch("application/pdf") },
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (fileName.isNotEmpty()) fileName else "File Laporan Akhir",
                        color = Color.Gray,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        Icons.Default.AttachFile,
                        contentDescription = "Attach file",
                        tint = Color.Gray
                    )
                }
            }

            Text(
                text = "Review",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            // Review Section
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable { navController.navigate("review")

                    },
                colors = CardDefaults.outlinedCardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Komentar...",
                        color = Color.Gray,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = "Lihat komentar",
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            when (uiState) {
                is PengajuanUiState.Success -> {
                    val item = (uiState as PengajuanUiState.Success).item
                    Button(
                        onClick = {
                            selectedFileUri?.let { uri ->
                                scope.launch {
                                    viewModel.uploadFile(context, uri)
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFA726)
                        ),
                        enabled = selectedFileUri != null
                    ) {
                        Text(if (item?.file != null) "Upload Revisi" else "Kirim")
                    }
                }
                is PengajuanUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is PengajuanUiState.Error -> {
                    Text(
                        text = (uiState as PengajuanUiState.Error).message,
                        color = Color.Red
                    )
                }
                else -> {}
            }
        }
    }
}

private fun getFileName(context: Context, uri: Uri): String {
    var result: String? = null
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                result = cursor.getString(nameIndex)
            }
        }
    }
    return result ?: "Unknown file"
} 