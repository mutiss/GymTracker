package com.mutissx.gymtracker.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutissx.gymtracker.domain.usecase.ObserveHistoryUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel(
    observeHistory: ObserveHistoryUseCase
) : ViewModel() {
    val uiState: StateFlow<HistoryUiState> = observeHistory()
        .map { days -> HistoryUiState(days = days, isLoading = false) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HistoryUiState())
}
