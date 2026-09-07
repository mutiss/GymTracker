package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.repository.ExerciseRepository

class DeleteExerciseEntryUseCase(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(id: Long) {
        exerciseRepository.deleteEntry(id)
    }
}
