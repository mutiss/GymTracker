package com.mutissx.gymtracker.presentation.history

import com.mutissx.gymtracker.domain.model.DaySummary

data class HistoryUiState(
    val days: List<DaySummary> = emptyList(),
    val isLoading: Boolean = true
)
