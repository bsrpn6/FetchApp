package me.brandonray.fetchapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.brandonray.fetchapp.composables.AppNavigation
import me.brandonray.fetchapp.ui.theme.FetchAppTheme
import me.brandonray.fetchapp.viewmodel.ItemViewModel

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val itemViewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        } else {
            null
        }
        super.onCreate(savedInstanceState)

        splashScreen?.setKeepOnScreenCondition {
            itemViewModel.items.value.isEmpty()
        }

        setContent {
            FetchAppTheme {
                AppNavigation(itemViewModel = itemViewModel)
            }
        }

        lifecycleScope.launch {
            itemViewModel.items.collect { items ->
                Log.d(TAG, "Received items: $items")
            }
        }
    }
}