package com.example.jumatexpress

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

sealed class PengajuanUiState {
    object Initial : PengajuanUiState()
    object Loading : PengajuanUiState()
    data class Success(val item: Item?) : PengajuanUiState()
    data class Error(val message: String) : PengajuanUiState()
}

class PengajuanViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<PengajuanUiState>(PengajuanUiState.Initial)
    val uiState: StateFlow<PengajuanUiState> = _uiState

    fun getItems() {
        viewModelScope.launch {
            _uiState.value = PengajuanUiState.Loading
            try {
                val response = ApiClient.instance.create(ApiService::class.java).getItems()
                if (response.isSuccessful) {
                    val items = response.body()
                    _uiState.value = PengajuanUiState.Success(items?.firstOrNull())
                } else {
                    _uiState.value = PengajuanUiState.Error("Failed to load items")
                }
            } catch (e: Exception) {
                _uiState.value = PengajuanUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun uploadFile(context: Context, uri: Uri) {
        _uiState.value = PengajuanUiState.Loading
        try {
            // Convert Uri to File
            val file = createTempFileFromUri(context, uri)
            
            // Create MultipartBody.Part
            val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            // Upload file
            val response = ApiClient.getApiService(context).uploadFile(body)
            if (response.isSuccessful) {
                _uiState.value = PengajuanUiState.Success(response.body()?.data)
            } else {
                _uiState.value = PengajuanUiState.Error("Upload failed")
            }
        } catch (e: Exception) {
            _uiState.value = PengajuanUiState.Error(e.message ?: "Unknown error")
        }
    }

    private fun createTempFileFromUri(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = getFileName(context, uri)
        val file = File(context.cacheDir, fileName)
        
        FileOutputStream(file).use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
        
        return file
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
        return result ?: "file.pdf"
    }
} 