package me.brandonray.fetchapp.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.Flow
import me.brandonray.fetchapp.BuildConfig
import me.brandonray.fetchapp.R
import me.brandonray.fetchapp.composables.drawer.ScaffoldedScreen
import me.brandonray.fetchapp.composables.screens.AboutScreen
import me.brandonray.fetchapp.composables.screens.MainContent
import me.brandonray.fetchapp.composables.screens.SettingsScreen
import me.brandonray.fetchapp.viewmodel.ItemViewModel

@Composable
fun AppNavigation(
    itemViewModel: ItemViewModel,
    onDarkModeToggle: (Boolean) -> Unit,
    isDarkModeFlow: Flow<Boolean>
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = "main"
    ) {
        composable("main") {
            MainContent(itemViewModel = itemViewModel, navController = navController)
        }
        composable("about") {
            ScaffoldedScreen(
                title = stringResource(R.string.about), navController = navController
            ) {
                AboutScreen()
            }
        }
        composable("settings") {
            ScaffoldedScreen(
                title = stringResource(R.string.settings),
                navController = navController
            ) {
                SettingsScreen(
                    isDarkModeFlow = isDarkModeFlow,
                    onDarkModeToggle = onDarkModeToggle,
                    onClearCache = {
                        itemViewModel.clearCache()
                    },
                    appVersion = BuildConfig.VERSION_NAME
                )
            }
        }
    }
}










