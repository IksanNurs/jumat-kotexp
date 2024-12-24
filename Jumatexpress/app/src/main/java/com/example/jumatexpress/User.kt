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

data class Item(
    val id: Int,
    val id_mahasiswa: Int,
    val file: String?,
    val revisi: String?
)
