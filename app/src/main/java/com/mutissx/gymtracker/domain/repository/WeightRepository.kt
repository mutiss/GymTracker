package com.mutissx.gymtracker.domain.repository

import com.mutissx.gymtracker.domain.model.WeightEntry
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface WeightRepository {
    suspend fun upsertWeight(date: LocalDate, weightKg: Double)
    fun observeWeightForDate(date: LocalDate): Flow<WeightEntry?>
    fun observeLatestWeight(): Flow<WeightEntry?>
    fun observeAllWeights(): Flow<List<WeightEntry>>
    fun observeWeightsInRange(start: LocalDate, end: LocalDate): Flow<List<WeightEntry>>
}
