package me.brandonray.fetchapp.composables.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import me.brandonray.fetchapp.composables.drawer.DrawerContent
import me.brandonray.fetchapp.composables.drawer.Header
import me.brandonray.fetchapp.composables.list.CollapsibleItemList
import me.brandonray.fetchapp.viewmodel.ItemViewModel

@Composable
fun MainContent(itemViewModel: ItemViewModel, navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        DrawerContent(onItemClick = { route ->
            scope.launch { drawerState.close() }
            navController.navigate(route) {
                popUpTo("main") { inclusive = false }
            }
        })
    }) {
        Column {
            Header(
                drawerState = drawerState,
                navController = navController,
                isHome = navController.currentDestination?.route == "main"
            )
            CollapsibleItemList(items = itemViewModel.items.collectAsState().value)
        }
    }
}