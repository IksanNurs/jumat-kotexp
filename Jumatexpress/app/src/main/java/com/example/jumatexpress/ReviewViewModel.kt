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

class ReviewViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<List<Review>>(emptyList())
    val uiState: StateFlow<List<Review>> = _uiState
    private val context = application.applicationContext

    fun getReviews() {
        viewModelScope.launch {
            try {
                val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                val token = sharedPreferences.getString("jwt_token", "") ?: ""
                
                val response = ApiClient.getApiService(context).getReviews("Bearer $token")
                if (response.isSuccessful) {
                    _uiState.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}