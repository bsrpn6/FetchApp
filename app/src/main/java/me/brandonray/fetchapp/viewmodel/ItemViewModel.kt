package me.brandonray.fetchapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.brandonray.fetchapp.models.Item
import me.brandonray.fetchapp.repository.FilterMode
import me.brandonray.fetchapp.repository.ItemRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _lastUpdated = MutableStateFlow<String?>(null)
    val lastUpdated: StateFlow<String?> = _lastUpdated.asStateFlow()

    private val _filterMode = MutableStateFlow(FilterMode.ALL)
    val filterMode: StateFlow<FilterMode> = _filterMode.asStateFlow()

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = combine(_items, _filterMode) { items, filterMode ->
        when (filterMode) {
            FilterMode.ALL -> items
            FilterMode.EXCLUDE_NULL_EMPTY -> items.filter {
                !it.name.equals(
                    "Unknown",
                    ignoreCase = true
                ) && !it.name.isNullOrEmpty()
            }

            FilterMode.EVEN_CHARACTER_LENGTH -> items.filter { it.name?.length?.rem(2) == 0 }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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
                _lastUpdated.value = "Last updated: $currentTime"
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

    fun updateFilterMode(newFilter: FilterMode) {
        _filterMode.value = newFilter
    }

    fun clearCache() {
        viewModelScope.launch {
            repository.clearCache()
        }
    }
}