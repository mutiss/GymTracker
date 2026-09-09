package com.mutissx.gymtracker.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class HydrationEntry(
    val id: Long = 0L,
    val date: LocalDate,
    val hydrated: Boolean,
    val updatedAt: LocalDateTime
)
