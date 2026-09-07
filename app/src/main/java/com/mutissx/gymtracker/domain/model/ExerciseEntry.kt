package com.mutissx.gymtracker.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class ExerciseEntry(
    val id: Long = 0L,
    val date: LocalDate,
    val category: ExerciseCategory,
    val value: Double,
    val unit: ValueUnit,
    val createdAt: LocalDateTime
)
