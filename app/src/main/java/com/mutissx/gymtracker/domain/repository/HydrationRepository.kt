package com.mutissx.gymtracker.domain.repository

import com.mutissx.gymtracker.domain.model.HydrationEntry
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface HydrationRepository {
    suspend fun setHydrated(date: LocalDate, hydrated: Boolean)
    fun observeHydrationForDate(date: LocalDate): Flow<HydrationEntry?>
    fun observeAllHydration(): Flow<List<HydrationEntry>>
}
