package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.repository.WeightRepository
import java.time.LocalDate

class LogWeightUseCase(
    private val weightRepository: WeightRepository
) {
    suspend operator fun invoke(date: LocalDate, weightKg: Double) {
        require(weightKg > 0) { "Weight must be greater than 0" }
        weightRepository.upsertWeight(date, weightKg)
    }
}
