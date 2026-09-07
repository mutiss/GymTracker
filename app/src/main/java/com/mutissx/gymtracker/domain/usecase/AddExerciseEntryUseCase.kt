package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.model.ExerciseCategory
import com.mutissx.gymtracker.domain.model.ExerciseEntry
import com.mutissx.gymtracker.domain.model.ValueUnit
import com.mutissx.gymtracker.domain.repository.ExerciseRepository
import java.time.LocalDate
import java.time.LocalDateTime

class AddExerciseEntryUseCase(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(date: LocalDate, category: ExerciseCategory, value: Double, unit: ValueUnit) {
        require(value > 0) { "Value must be greater than 0" }
        exerciseRepository.addEntry(
            ExerciseEntry(
                date = date,
                category = category,
                value = value,
                unit = unit,
                createdAt = LocalDateTime.now()
            )
        )
    }
}
