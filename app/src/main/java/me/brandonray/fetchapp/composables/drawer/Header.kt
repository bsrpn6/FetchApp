package me.brandonray.fetchapp.composables.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import me.brandonray.fetchapp.R

@Composable
fun Header(
    drawerState: DrawerState?,
    navController: NavController,
    isHome: Boolean,
    title: String = stringResource(R.string.app_name)
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isHome) {
            Icon(imageVector = Icons.Default.Menu,
                contentDescription = stringResource(R.string.menu),
                modifier = Modifier
                    .clickable { scope.launch { drawerState?.open() } }
                    .padding(end = 16.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        } else {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .clickable { navController.navigateUp() }
                    .padding(end = 16.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}