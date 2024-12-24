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
import android.content.Intent
import android.content.ContentValues
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import kotlinx.coroutines.delay


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
      var showDialog by remember { mutableStateOf(false) }

      // State untuk menyimpan item dari API
      var item by remember { mutableStateOf<Item?>(null) }
      var isLoading by remember { mutableStateOf(true) }

      // Add state for showing success message
      var showSuccessMessage by remember { mutableStateOf(false) }

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
                  .verticalScroll(rememberScrollState()), // Add scrolling
              verticalArrangement = Arrangement.spacedBy(16.dp)
          ) {
              if (showSuccessMessage) {
                  Card(
                      modifier = Modifier
                          .fillMaxWidth()
                          .padding(bottom = 8.dp),
                      colors = CardDefaults.cardColors(
                          containerColor = Color(0xFFE8F5E9)
                      )
                  ) {
                      Row(
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(16.dp),
                          verticalAlignment = Alignment.CenterVertically
                      ) {
                          Icon(
                              Icons.Default.CheckCircle,
                              contentDescription = "Success",
                              tint = Color(0xFF4CAF50),
                              modifier = Modifier.size(24.dp)
                          )
                          Spacer(modifier = Modifier.width(16.dp))
                          Text(
                              text = "File berhasil diupload",
                              style = MaterialTheme.typography.bodyMedium,
                              color = Color(0xFF2E7D32)
                          )
                      }
                  }
              }

              Column(
                  modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
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

                  if (selectedFileUri != null) {
                      OutlinedCard(
                          modifier = Modifier
                              .fillMaxWidth()
                              .padding(bottom = 8.dp),
                          colors = CardDefaults.outlinedCardColors(
                              containerColor = Color(0xFFF5F5F5)
                          )
                      ) {
                          Row(
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .padding(16.dp),
                              verticalAlignment = Alignment.CenterVertically
                          ) {
                              Icon(
                                  Icons.Default.PictureAsPdf,
                                  contentDescription = "Selected file",
                                  tint = Color.Red,
                                  modifier = Modifier.size(24.dp)
                              )
                              Spacer(modifier = Modifier.width(16.dp))
                              Text(
                                  text = fileName,
                                  style = MaterialTheme.typography.bodyMedium,
                                  color = Color.DarkGray,
                                  modifier = Modifier.weight(1f)
                              )
                              IconButton(onClick = { selectedFileUri = null }) {
                                  Icon(
                                      Icons.Default.Close,
                                      contentDescription = "Remove file",
                                      tint = Color.Gray
                                  )
                              }
                          }
                      }
                  }
                  OutlinedCard(
                      modifier = Modifier
                          .fillMaxWidth()
                          .clickable { showDialog = true },
                      colors = CardDefaults.outlinedCardColors(
                          containerColor = Color(0xFFF5F5F5)
                      )
                  ) {
                      Column(
                          modifier = Modifier.padding(16.dp)
                      ) {
                          Row(
                              modifier = Modifier
                                  .fillMaxWidth()
                                  .height(56.dp),
                              verticalAlignment = Alignment.CenterVertically
                          ) {
                              Text(
                                  text = "File Laporan Akhir",
                                  style = MaterialTheme.typography.titleMedium,
                                  color = Color.Gray,
                                  modifier = Modifier.weight(1f)
                              )
                              Icon(
                                  Icons.Default.Add,
                                  contentDescription = "Add file",
                                  tint = Color(0xFFFFA726)
                              )
                          }
                          val context = LocalContext.current
                          var showDeleteSuccess by remember { mutableStateOf(false) }

                          when (uiState) {
                              is PengajuanUiState.Success -> {
                                  val items = (uiState as PengajuanUiState.Success).items
                                  items.forEach { item ->
                                      if (item.file != null) {
                                          Divider(
                                              modifier = Modifier.padding(vertical = 8.dp),
                                              color = Color.LightGray
                                          )
                                          Row(
                                              modifier = Modifier
                                                  .fillMaxWidth()
                                                  .clickable {
                                                      val intent = Intent(Intent.ACTION_VIEW).apply {
                                                          data = Uri.parse("${ApiClient.BASE_URL}uploads/${item.file}")
                                                          flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                                      }
                                                      context.startActivity(intent)
                                                  }
                                                  .padding(vertical = 8.dp),
                                              verticalAlignment = Alignment.CenterVertically,
                                              horizontalArrangement = Arrangement.SpaceBetween
                                          ) {
                                              Row(
                                                  verticalAlignment = Alignment.CenterVertically,
                                                  modifier = Modifier.weight(1f)
                                              ) {
                                                  Icon(
                                                      Icons.Default.PictureAsPdf,
                                                      contentDescription = "PDF file",
                                                      tint = Color.Red,
                                                      modifier = Modifier.size(24.dp)
                                                  )
                                                  Spacer(modifier = Modifier.width(16.dp))
                                                  Text(
                                                      text = item.file,
                                                      style = MaterialTheme.typography.bodyMedium,
                                                      color = Color.DarkGray
                                                  )
                                              }
                                              IconButton(
                                                  onClick = {
                                                      viewModel.deleteItem(item.id)
                                                      showDeleteSuccess = true
                                                      scope.launch {
                                                          delay(2000)
                                                          showDeleteSuccess = false
                                                      }
                                                        viewModel.getItems() // Refresh items list
                                                  }
                                              ) {
                                                  Icon(
                                                      Icons.Default.Close,
                                                      contentDescription = "Delete file",
                                                      tint = Color.Gray
                                                  )
                                              }
                                          }
                                      }
                                  }
                              }
                              else -> {}
                          }

                          if (showDeleteSuccess) {
                              Toast.makeText(context, "File berhasil dihapus", Toast.LENGTH_SHORT).show()
                          }
                      }
                  }

                  Text(
                      text = "Review",
                      style = MaterialTheme.typography.titleMedium.copy(
                          fontWeight = FontWeight.Bold
                      )
                  )

                  val launcher = rememberLauncherForActivityResult(
                      contract = ActivityResultContracts.GetContent()
                  ) { uri: Uri? ->
                      uri?.let {
                          selectedFileUri = it
                          fileName = getFileName(context, it)
                      }
                  }

                  val cameraLauncher = rememberLauncherForActivityResult(
                      contract = ActivityResultContracts.TakePicture()
                  ) { success ->
                      if (success) {
                          // Handle camera capture success
                      }
                  }

                  if (showDialog) {
                      AlertDialog(
                          onDismissRequest = { showDialog = false },
                          title = { Text("Pilih Sumber") },
                          text = {
                              Column {
                                  TextButton(
                                      onClick = {
                                          launcher.launch("*/*")
                                          showDialog = false
                                      }
                                  ) {
                                      Text("Gallery")
                                  }
                                  TextButton(
                                      onClick = {
                                          cameraLauncher.launch(createImageUri(context))
                                          showDialog = false
                                      }
                                  ) {
                                      Text("Camera")
                                  }
                              }
                          },
                          confirmButton = {},
                          dismissButton = {
                              TextButton(onClick = { showDialog = false }) {
                                  Text("Batal")
                              }
                          }
                      )
                  }
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
                          val items = (uiState as? PengajuanUiState.Success)?.items
                       Button(
    onClick = {
        selectedFileUri?.let { uri ->
            scope.launch {
                viewModel.uploadFile(context, uri)
                showSuccessMessage = true
                selectedFileUri = null // Clear selected file
                viewModel.getItems() // Refresh items list
                scope.launch {
                    delay(3000)
                    showSuccessMessage = false
                }
            }
        }
    },
        modifier = Modifier
                                  .fillMaxWidth()
                                   .padding(horizontal = 16.dp, vertical = 8.dp)
                                  .height(48.dp),
                              colors = ButtonDefaults.buttonColors(
                                  containerColor = Color(0xFFFFA726)
                              ),

    enabled = selectedFileUri != null
) {
    val items = (uiState as? PengajuanUiState.Success)?.items
    Text(if (items?.isNotEmpty() == true) "Upload Revisi" else "Upload File")
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

private fun createImageUri(context: Context): Uri {
    val timeStamp = System.currentTimeMillis()
    val imageFileName = "JPEG_${timeStamp}_"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, imageFileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
    }
    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    ) ?: throw IllegalStateException("Failed to create image URI")
}
