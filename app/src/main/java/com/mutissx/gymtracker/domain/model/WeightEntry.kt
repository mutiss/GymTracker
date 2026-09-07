package com.mutissx.gymtracker.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class WeightEntry(
    val id: Long = 0L,
    val date: LocalDate,
    val weightKg: Double,
    val updatedAt: LocalDateTime
)
