package me.brandonray.fetchapp.repository

import android.util.Log
import me.brandonray.fetchapp.models.Item
import me.brandonray.fetchapp.network.ApiService
import javax.inject.Inject

private const val TAG = "ItemRepository"

class ItemRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchItems(): List<Item> {
        val items = apiService.getItems()
        Log.d(TAG, "Fetched items: $items")
        return items
    }
}