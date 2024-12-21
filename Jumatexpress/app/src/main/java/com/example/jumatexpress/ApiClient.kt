package com.example.jumatexpress

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8081/"

    val instance: Retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL) 
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Fungsi untuk menyediakan Retrofit dengan Interceptor
    fun getRetrofit(context: Context): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = getToken(context) // Ambil token dari SharedPreferences
                val request = if (token != null) {
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token") // Tambahkan token ke header
                        .build()
                } else {
                    chain.request()
                }
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Tambahkan OkHttpClient dengan Interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Fungsi untuk menyediakan ApiService
    fun getApiService(context: Context): ApiService {
        return getRetrofit(context).create(ApiService::class.java)
    }

    // Fungsi untuk mengambil token dari SharedPreferences
    private fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("jwt_token", null)
    }
}
