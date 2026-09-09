package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.model.DaySummary
import com.mutissx.gymtracker.domain.repository.ExerciseRepository
import com.mutissx.gymtracker.domain.repository.HydrationRepository
import com.mutissx.gymtracker.domain.repository.WeightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ObserveHistoryUseCase(
    private val exerciseRepository: ExerciseRepository,
    private val weightRepository: WeightRepository,
    private val hydrationRepository: HydrationRepository
) {
    operator fun invoke(): Flow<List<DaySummary>> =
        combine(
            exerciseRepository.observeAllEntries(),
            weightRepository.observeAllWeights(),
            hydrationRepository.observeAllHydration()
        ) { exercises, weights, hydrationEntries ->
            val exercisesByDate = exercises.groupBy { it.date }
            val weightsByDate = weights.associateBy { it.date }
            val hydratedDates = hydrationEntries.filter { it.hydrated }.map { it.date }.toSet()
            val allDates = (exercisesByDate.keys + weightsByDate.keys + hydratedDates)
                .toSortedSet(compareByDescending { it })
            allDates.map { date ->
                DaySummary(
                    date = date,
                    exercises = exercisesByDate[date].orEmpty(),
                    weight = weightsByDate[date],
                    hydrated = hydratedDates.contains(date)
                )
            }
        }
}
