package com.mutissx.gymtracker.presentation.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutissx.gymtracker.domain.model.ChartGranularity
import com.mutissx.gymtracker.domain.usecase.ObserveWeightChartUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProgressViewModel(
    private val observeWeightChart: ObserveWeightChartUseCase
) : ViewModel() {

    private val granularity = MutableStateFlow(ChartGranularity.DAY)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ProgressUiState> = granularity
        .flatMapLatest { selected ->
            observeWeightChart(selected).map { points ->
                ProgressUiState(granularity = selected, points = points, isLoading = false)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProgressUiState())

    fun onGranularitySelected(selected: ChartGranularity) {
        granularity.value = selected
    }
}
