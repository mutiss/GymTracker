package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.model.TodayLog
import com.mutissx.gymtracker.domain.repository.ExerciseRepository
import com.mutissx.gymtracker.domain.repository.HydrationRepository
import com.mutissx.gymtracker.domain.repository.WeightRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ObserveTodayLogUseCase(
    private val exerciseRepository: ExerciseRepository,
    private val weightRepository: WeightRepository,
    private val hydrationRepository: HydrationRepository
) {
    operator fun invoke(today: LocalDate = LocalDate.now()): Flow<TodayLog> =
        combine(
            exerciseRepository.observeEntriesForDate(today),
            weightRepository.observeWeightForDate(today),
            hydrationRepository.observeHydrationForDate(today)
        ) { exercises, weight, hydration ->
            TodayLog(exercises = exercises, weight = weight, hydrated = hydration?.hydrated ?: false)
        }
}
