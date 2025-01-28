package me.brandonray.fetchapp.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.brandonray.fetchapp.models.Item
import me.brandonray.fetchapp.network.ApiService
import me.brandonray.fetchapp.room.entity.ItemDao
import me.brandonray.fetchapp.room.entity.ItemEntity
import javax.inject.Inject

private const val TAG = "ItemRepository"

class ItemRepository @Inject constructor(
    private val apiService: ApiService,
    private val itemDao: ItemDao
) {
    fun getItems(): Flow<List<Item>> {
        return itemDao.getAllItems().map { entities ->
            entities.map { Item(it.id, it.listId, it.name) }
                .sortedWith(compareBy({ it.listId }, { it.name ?: "" }))
        }
    }

    //TODO - implement update strategy instead of clear and re-insert
    suspend fun refreshItems() {
        try {
            val items = apiService.getItems().map {
                it.copy(name = it.name?.takeIf { it.isNotBlank() } ?: "Unknown")
            }
            itemDao.clearItems()
            itemDao.insertItems(items.map { ItemEntity(it.id, it.listId, it.name!!) })
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing items", e)
            throw e
        }
    }

    suspend fun clearCache() {
        itemDao.clearItems()
    }
}