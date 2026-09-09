package com.mutissx.gymtracker.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutissx.gymtracker.domain.model.ExerciseCategory
import com.mutissx.gymtracker.domain.model.ValueUnit
import com.mutissx.gymtracker.domain.usecase.AddExerciseEntryUseCase
import com.mutissx.gymtracker.domain.usecase.DeleteExerciseEntryUseCase
import com.mutissx.gymtracker.domain.usecase.LogWeightUseCase
import com.mutissx.gymtracker.domain.usecase.ObserveTodayLogUseCase
import com.mutissx.gymtracker.domain.usecase.SetHydrationUseCase
import java.time.LocalDate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodayViewModel(
    private val observeTodayLog: ObserveTodayLogUseCase,
    private val addExerciseEntry: AddExerciseEntryUseCase,
    private val logWeight: LogWeightUseCase,
    private val deleteExerciseEntry: DeleteExerciseEntryUseCase,
    private val setHydration: SetHydrationUseCase
) : ViewModel() {

    private val today = LocalDate.now()

    val uiState: StateFlow<TodayUiState> = observeTodayLog(today)
        .map { log ->
            TodayUiState(exercises = log.exercises, weight = log.weight, hydrated = log.hydrated, isLoading = false)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TodayUiState())

    fun onAddExercise(category: ExerciseCategory, value: Double, unit: ValueUnit) {
        viewModelScope.launch { addExerciseEntry(today, category, value, unit) }
    }

    fun onLogWeight(weightKg: Double) {
        viewModelScope.launch { logWeight(today, weightKg) }
    }

    fun onDeleteExercise(id: Long) {
        viewModelScope.launch { deleteExerciseEntry(id) }
    }

    fun onToggleHydration() {
        viewModelScope.launch { setHydration(today, !uiState.value.hydrated) }
    }
}
