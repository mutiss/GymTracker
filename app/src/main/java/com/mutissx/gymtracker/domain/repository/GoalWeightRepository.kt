package com.mutissx.gymtracker.domain.repository

import kotlinx.coroutines.flow.Flow

interface GoalWeightRepository {
    fun observeGoalWeight(): Flow<Double?>
    suspend fun setGoalWeight(weightKg: Double)
}
