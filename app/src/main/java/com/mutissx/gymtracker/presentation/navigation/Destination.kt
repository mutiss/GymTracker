package com.mutissx.gymtracker.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Today
import androidx.compose.ui.graphics.vector.ImageVector
import com.mutissx.gymtracker.R

sealed class Destination(val route: String, @StringRes val labelRes: Int, val icon: ImageVector) {
    data object Today : Destination("today", R.string.nav_today, Icons.Default.Today)
    data object History : Destination("history", R.string.nav_history, Icons.Default.CalendarMonth)
    data object Progress : Destination("progress", R.string.nav_progress, Icons.AutoMirrored.Filled.ShowChart)

    companion object {
        val bottomBarDestinations = listOf(Today, History, Progress)
    }
}
