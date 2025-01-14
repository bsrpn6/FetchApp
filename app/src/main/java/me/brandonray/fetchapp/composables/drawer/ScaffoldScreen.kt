package me.brandonray.fetchapp.composables.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ScaffoldedScreen(
    title: String, navController: NavController, content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Header(
            drawerState = null, navController = navController, isHome = false, title = title
        )
        content()
    }
}