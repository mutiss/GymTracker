package com.mutissx.gymtracker.domain.repository

import com.mutissx.gymtracker.domain.model.ExerciseEntry
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun addEntry(entry: ExerciseEntry): Long
    suspend fun deleteEntry(id: Long)
    fun observeEntriesForDate(date: LocalDate): Flow<List<ExerciseEntry>>
    fun observeAllEntries(): Flow<List<ExerciseEntry>>
}
