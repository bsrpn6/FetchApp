package me.brandonray.fetchapp.network

import me.brandonray.fetchapp.models.Item
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}