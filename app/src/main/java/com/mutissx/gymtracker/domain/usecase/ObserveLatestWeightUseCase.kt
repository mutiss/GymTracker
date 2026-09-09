package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.model.WeightEntry
import com.mutissx.gymtracker.domain.repository.WeightRepository
import kotlinx.coroutines.flow.Flow

class ObserveLatestWeightUseCase(
    private val weightRepository: WeightRepository
) {
    operator fun invoke(): Flow<WeightEntry?> = weightRepository.observeLatestWeight()
}
