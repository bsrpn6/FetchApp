package me.brandonray.fetchapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.brandonray.fetchapp.models.Item
import me.brandonray.fetchapp.repository.ItemRepository
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val itemRepository: ItemRepository) : ViewModel() {
    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items.asStateFlow()

    init {
        fetchAndProcessItems()
    }

    private fun fetchAndProcessItems() {
        viewModelScope.launch {
            try {
                val fetchedItems = itemRepository.fetchItems()
                val processedItems = fetchedItems
                    .filter { !it.name.isNullOrBlank() }
                    .sortedWith(compareBy({ it.listId }, { it.name }))
                _items.value = processedItems
                Log.d("ItemViewModel", "Processed items: $processedItems")
            } catch (e: Exception) {
                Log.e("ItemViewModel", "Error fetching and processing items", e)
                _items.value = emptyList()
            }
        }
    }
}