package com.mutissx.gymtracker.domain.repository

import com.mutissx.gymtracker.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ThemePreferenceRepository {
    fun observeThemeMode(): Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)
}
