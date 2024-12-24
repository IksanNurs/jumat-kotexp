package com.example.jumatexpress

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import okhttp3.MultipartBody
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

//data class User(val name: String, val email: String, val password: String)

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @POST("users")
    suspend fun addUser(@Body user: User): Response<User>

    @POST("login") // Replace "login" with your actual login endpoint
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("/logbooks")
    suspend fun getLogbooks(
        @Query("id_mahasiswa") idMahasiswa: Int
    ): Response<List<Logbook>>

    @POST("reviews")
    suspend fun submitReview(@Body review: Review): Response<Review>

    @GET("items")
    suspend fun getItems(@Header("Authorization") token: String): Response<List<Item>>

    @DELETE("items/{id}")
    suspend fun deleteItem(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<DeleteResponse>

    @Multipart
    @POST("items")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part
    ): Response<UploadResponse>

    @GET("reviews")
    suspend fun getReviews(@Header("Authorization") token: String): Response<List<Review>>
}

data class Review(
    val id: Int,
    val id_item: Int,
    val description: String,
    val date: String
)

data class UploadResponse(
    val message: String,
    val data: Item
)

data class ReviewResponse(
    val id: Int,
    val id_item: Int,
    val description: String
)

data class DeleteResponse(
    val message: String,
    val deletedItemId: Int
)
