package me.brandonray.fetchapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.brandonray.fetchapp.composables.list.CollapsibleItemList
import me.brandonray.fetchapp.ui.theme.FetchAppTheme
import me.brandonray.fetchapp.viewmodel.ItemViewModel

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val itemViewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FetchAppTheme {
                val items by itemViewModel.items.collectAsState()
                CollapsibleItemList(items)
            }
        }

        lifecycleScope.launch {
            itemViewModel.items.collect { items ->
                Log.d(TAG, "Received items: $items")
            }
        }
    }
}