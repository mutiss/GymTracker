package com.mutissx.gymtracker.presentation.progress

import com.mutissx.gymtracker.domain.model.ChartGranularity
import com.mutissx.gymtracker.domain.model.ChartPoint

data class ProgressUiState(
    val granularity: ChartGranularity = ChartGranularity.DAY,
    val points: List<ChartPoint> = emptyList(),
    val latestWeightKg: Double? = null,
    val goalWeightKg: Double? = null,
    val isLoading: Boolean = true
)
