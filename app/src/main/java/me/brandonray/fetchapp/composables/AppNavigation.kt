package me.brandonray.fetchapp.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.brandonray.fetchapp.composables.list.CollapsibleItemList
import me.brandonray.fetchapp.viewmodel.ItemViewModel

@Composable
fun AppNavigation(itemViewModel: ItemViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainContent(itemViewModel = itemViewModel)
        }
    }
}

@Composable
fun MainContent(itemViewModel: ItemViewModel) {
    val items by itemViewModel.items.collectAsState()
    CollapsibleItemList(items = items)
}
