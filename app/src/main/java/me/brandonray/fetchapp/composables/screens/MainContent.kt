package me.brandonray.fetchapp.composables.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import me.brandonray.fetchapp.R
import me.brandonray.fetchapp.composables.drawer.DrawerContent
import me.brandonray.fetchapp.composables.drawer.Header
import me.brandonray.fetchapp.composables.list.CollapsibleItemList
import me.brandonray.fetchapp.viewmodel.ItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(itemViewModel: ItemViewModel, navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val items by itemViewModel.items.collectAsState()
    val isRefreshing by itemViewModel.isLoading.collectAsState()
    val lastUpdated by itemViewModel.lastUpdated.collectAsState()

    val pullToRefreshState = rememberPullToRefreshState()

    Log.d("MainContent", "Last updated: $lastUpdated")

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(onItemClick = { route ->
                scope.launch { drawerState.close() }
                navController.navigate(route) {
                    popUpTo("main") { inclusive = false }
                }
            })
        }
    ) {
        Scaffold(
            topBar = {
                Header(
                    drawerState = drawerState,
                    navController = navController,
                    isHome = navController.currentDestination?.route == "main"
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Pull-to-refresh content
                PullToRefreshBox(
                    state = pullToRefreshState,
                    isRefreshing = isRefreshing,
                    onRefresh = { itemViewModel.refreshItems() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 50.dp),
                    indicator = {
                        PullToRefreshDefaults.Indicator(
                            state = pullToRefreshState,
                            isRefreshing = isRefreshing,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                ) {
                    CollapsibleItemList(items = items)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (items.isEmpty()) {
                        Text(
                            text = stringResource(R.string.pull_to_refresh),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        Text(
                            text = lastUpdated ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}