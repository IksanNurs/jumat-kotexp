package com.example.jumatexpress

import android.app.Application
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.AndroidViewModel
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
    data class Success(val items: List<Item>) : PengajuanUiState() // Changed from item to items
    data class Error(val message: String) : PengajuanUiState()
}

class PengajuanViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<PengajuanUiState>(PengajuanUiState.Initial)
    val uiState: StateFlow<PengajuanUiState> = _uiState
    private val context = application.applicationContext


    fun deleteItem(itemId: Int) {
    viewModelScope.launch {
        _uiState.value = PengajuanUiState.Loading
        try {
            val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("jwt_token", "") ?: ""
            
            val response = ApiClient.getApiService(context).deleteItem(itemId, "Bearer $token")
            if (response.isSuccessful) {
                // Remove deleted item from current list
                val currentItems = (_uiState.value as? PengajuanUiState.Success)?.items ?: emptyList()
                val updatedItems = currentItems.filter { it.id != itemId }
                _uiState.value = PengajuanUiState.Success(updatedItems)
            } else {
                _uiState.value = PengajuanUiState.Error("Failed to delete item")
            }
        } catch (e: Exception) {
            _uiState.value = PengajuanUiState.Error(e.message ?: "Unknown error")
        }
    }
   }
    fun getItems() {
        viewModelScope.launch {
            _uiState.value = PengajuanUiState.Loading
            try {
                val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("jwt_token", "") ?: ""
                
                val response = ApiClient.instance.create(ApiService::class.java)
                    .getItems("Bearer $token")
                
                if (response.isSuccessful) {
                    val items = response.body()
                    _uiState.value = PengajuanUiState.Success(items ?: emptyList())
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
        val file = createTempFileFromUri(context, uri)
        val requestFile = file.asRequestBody("*/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val response = ApiClient.getApiService(context).uploadFile(body)
        if (response.isSuccessful) {
            // Get current items list
            val currentItems = (_uiState.value as? PengajuanUiState.Success)?.items ?: emptyList()
            // Add new item to list
            val newItems = currentItems + listOf(response.body()?.data)
            _uiState.value = PengajuanUiState.Success(newItems.filterNotNull())
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

