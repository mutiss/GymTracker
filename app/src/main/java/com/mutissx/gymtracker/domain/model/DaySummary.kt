package com.mutissx.gymtracker.domain.model

import java.time.LocalDate

data class DaySummary(
    val date: LocalDate,
    val exercises: List<ExerciseEntry>,
    val weight: WeightEntry?
)
