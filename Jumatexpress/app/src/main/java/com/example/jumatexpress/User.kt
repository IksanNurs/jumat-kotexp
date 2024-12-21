package com.example.jumatexpress

data class User(
    val id: String = "",
    val name: String,
    val email: String,
    val password: String
    // tambahkan field lain sesuai kebutuhan
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: User //
)

data class Logbook(
    val id: Int,
    val tanggal: String,
    val topik_pekerjaan: String,
    val deskripsi: String,
    val id_mahasiswa: Int
)

data class Review(
    val id: Int,
    val rating: Int,
    val review: String,
    val id_mahasiswa: Int,
    val created_at: String
)

data class Item(
    val id: Int,
    val id_mahasiswa: Int,
    val file: String?,
    val revisi: String?
)
