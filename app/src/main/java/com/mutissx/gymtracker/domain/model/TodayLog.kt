package com.mutissx.gymtracker.domain.model

data class TodayLog(
    val exercises: List<ExerciseEntry>,
    val weight: WeightEntry?,
    val hydrated: Boolean
)
