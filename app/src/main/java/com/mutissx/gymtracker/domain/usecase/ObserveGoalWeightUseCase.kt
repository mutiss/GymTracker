package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.repository.GoalWeightRepository
import kotlinx.coroutines.flow.Flow

class ObserveGoalWeightUseCase(
    private val goalWeightRepository: GoalWeightRepository
) {
    operator fun invoke(): Flow<Double?> = goalWeightRepository.observeGoalWeight()
}
