package com.mutissx.gymtracker.presentation.today

import com.mutissx.gymtracker.domain.model.ExerciseEntry
import com.mutissx.gymtracker.domain.model.WeightEntry

data class TodayUiState(
    val exercises: List<ExerciseEntry> = emptyList(),
    val weight: WeightEntry? = null,
    val hydrated: Boolean = false,
    val isLoading: Boolean = true
)
