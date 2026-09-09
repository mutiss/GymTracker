package com.mutissx.gymtracker.presentation.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutissx.gymtracker.domain.model.ChartGranularity
import com.mutissx.gymtracker.domain.usecase.ObserveGoalWeightUseCase
import com.mutissx.gymtracker.domain.usecase.ObserveLatestWeightUseCase
import com.mutissx.gymtracker.domain.usecase.ObserveWeightChartUseCase
import com.mutissx.gymtracker.domain.usecase.SetGoalWeightUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProgressViewModel(
    private val observeWeightChart: ObserveWeightChartUseCase,
    observeLatestWeight: ObserveLatestWeightUseCase,
    observeGoalWeight: ObserveGoalWeightUseCase,
    private val setGoalWeight: SetGoalWeightUseCase
) : ViewModel() {

    private val granularity = MutableStateFlow(ChartGranularity.DAY)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val chartPoints = granularity.flatMapLatest { observeWeightChart(it) }

    val uiState: StateFlow<ProgressUiState> = combine(
        chartPoints,
        granularity,
        observeLatestWeight(),
        observeGoalWeight()
    ) { points, selectedGranularity, latestWeight, goalWeight ->
        ProgressUiState(
            granularity = selectedGranularity,
            points = points,
            latestWeightKg = latestWeight?.weightKg,
            goalWeightKg = goalWeight,
            isLoading = false
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProgressUiState())

    fun onGranularitySelected(selected: ChartGranularity) {
        granularity.value = selected
    }

    fun onSetGoalWeight(weightKg: Double) {
        viewModelScope.launch { setGoalWeight(weightKg) }
    }
}
