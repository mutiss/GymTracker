package com.mutissx.gymtracker.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Today
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destination(val route: String, val label: String, val icon: ImageVector) {
    data object Today : Destination("today", "Today", Icons.Default.Today)
    data object History : Destination("history", "History", Icons.Default.CalendarMonth)
    data object Progress : Destination("progress", "Progress", Icons.AutoMirrored.Filled.ShowChart)

    companion object {
        val bottomBarDestinations = listOf(Today, History, Progress)
    }
}
