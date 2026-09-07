package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.model.ExerciseEntry
import com.mutissx.gymtracker.domain.model.WeightEntry
import com.mutissx.gymtracker.domain.repository.ExerciseRepository
import com.mutissx.gymtracker.domain.repository.WeightRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ObserveTodayLogUseCase(
    private val exerciseRepository: ExerciseRepository,
    private val weightRepository: WeightRepository
) {
    operator fun invoke(today: LocalDate = LocalDate.now()): Flow<Pair<List<ExerciseEntry>, WeightEntry?>> =
        combine(
            exerciseRepository.observeEntriesForDate(today),
            weightRepository.observeWeightForDate(today)
        ) { exercises, weight -> exercises to weight }
}
