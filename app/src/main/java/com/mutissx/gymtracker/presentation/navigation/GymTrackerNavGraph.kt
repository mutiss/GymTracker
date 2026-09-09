package com.mutissx.gymtracker.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mutissx.gymtracker.R
import com.mutissx.gymtracker.domain.model.ThemeMode
import com.mutissx.gymtracker.presentation.common.GymTrackerBottomBar
import com.mutissx.gymtracker.presentation.history.HistoryScreen
import com.mutissx.gymtracker.presentation.progress.ProgressScreen
import com.mutissx.gymtracker.presentation.today.TodayScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymTrackerNavGraph(
    themeMode: ThemeMode,
    onCycleThemeMode: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val currentDestination = Destination.bottomBarDestinations.firstOrNull { it.route == currentRoute }
        ?: Destination.Today

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(currentDestination.labelRes)) },
                actions = {
                    IconButton(onClick = onCycleThemeMode) {
                        Icon(
                            imageVector = when (themeMode) {
                                ThemeMode.SYSTEM -> Icons.Default.BrightnessAuto
                                ThemeMode.LIGHT -> Icons.Default.LightMode
                                ThemeMode.DARK -> Icons.Default.DarkMode
                            },
                            contentDescription = stringResource(
                                R.string.toggle_theme_content_description,
                                themeMode.name.lowercase()
                            )
                        )
                    }
                }
            )
        },
        bottomBar = { GymTrackerBottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Today.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destination.Today.route) { TodayScreen() }
            composable(Destination.History.route) { HistoryScreen() }
            composable(Destination.Progress.route) { ProgressScreen() }
        }
    }
}
