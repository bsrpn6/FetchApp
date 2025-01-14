package me.brandonray.fetchapp

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
import me.brandonray.fetchapp.ui.theme.ThemePreferenceManager
import me.brandonray.fetchapp.viewmodel.ItemViewModel

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val itemViewModel: ItemViewModel by viewModels()
    private lateinit var themePreferenceManager: ThemePreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        themePreferenceManager = ThemePreferenceManager(applicationContext)

        splashScreen.setKeepOnScreenCondition {
            itemViewModel.items.value.isEmpty()
        }

        setContent {
            FetchAppTheme(isDarkModeFlow = themePreferenceManager.isDarkMode) {
                AppNavigation(
                    itemViewModel = itemViewModel,
                    onDarkModeToggle = { newValue ->
                        lifecycleScope.launch {
                            try {
                                themePreferenceManager.setDarkMode(newValue)
                            } catch (e: Exception) {
                                Log.e(TAG, "Error updating dark mode preference", e)
                            }
                        }
                    },
                    isDarkModeFlow = themePreferenceManager.isDarkMode
                )
            }
        }

        lifecycleScope.launch {
            itemViewModel.refreshItems()
        }
    }
}