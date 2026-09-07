package com.mutissx.gymtracker.domain.model

import java.time.LocalDate

data class ChartPoint(
    val date: LocalDate,
    val label: String,
    val weightKg: Double
)
