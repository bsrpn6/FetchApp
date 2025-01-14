package me.brandonray.fetchapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.brandonray.fetchapp.models.Item
import me.brandonray.fetchapp.repository.ItemRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ItemRepository,
    @Named("lastUpdated") private val lastUpdatedString: String
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _lastUpdated = MutableStateFlow<String?>(null)
    val lastUpdated: StateFlow<String?> = _lastUpdated

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items.asStateFlow()

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getItems().collect { fetchedItems ->
                _items.value = fetchedItems
                _isLoading.value = false
                val currentTime =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                _lastUpdated.value = "$lastUpdatedString $currentTime"
            }
        }
    }

    fun refreshItems() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.refreshItems()
            repository.getItems().collect { refreshedItems ->
                _items.value = refreshedItems
                _isLoading.value = false
            }
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            repository.clearCache()
        }
    }
}