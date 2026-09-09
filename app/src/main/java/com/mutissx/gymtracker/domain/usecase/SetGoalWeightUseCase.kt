package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.repository.GoalWeightRepository

class SetGoalWeightUseCase(
    private val goalWeightRepository: GoalWeightRepository
) {
    suspend operator fun invoke(weightKg: Double) {
        goalWeightRepository.setGoalWeight(weightKg)
    }
}
