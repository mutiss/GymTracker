package com.mutissx.gymtracker.di

import com.mutissx.gymtracker.domain.usecase.AddExerciseEntryUseCase
import com.mutissx.gymtracker.domain.usecase.DeleteExerciseEntryUseCase
import com.mutissx.gymtracker.domain.usecase.LogWeightUseCase
import com.mutissx.gymtracker.domain.usecase.ObserveGoalWeightUseCase
import com.mutissx.gymtracker.domain.usecase.ObserveHistoryUseCase
import com.mutissx.gymtracker.domain.usecase.ObserveLatestWeightUseCase
import com.mutissx.gymtracker.domain.usecase.ObserveThemeModeUseCase
import com.mutissx.gymtracker.domain.usecase.ObserveTodayLogUseCase
import com.mutissx.gymtracker.domain.usecase.ObserveWeightChartUseCase
import com.mutissx.gymtracker.domain.usecase.SetGoalWeightUseCase
import com.mutissx.gymtracker.domain.usecase.SetHydrationUseCase
import com.mutissx.gymtracker.domain.usecase.SetThemeModeUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { AddExerciseEntryUseCase(get()) }
    factory { LogWeightUseCase(get()) }
    factory { DeleteExerciseEntryUseCase(get()) }
    factory { ObserveTodayLogUseCase(get(), get(), get()) }
    factory { ObserveHistoryUseCase(get(), get(), get()) }
    factory { ObserveWeightChartUseCase(get()) }
    factory { ObserveThemeModeUseCase(get()) }
    factory { SetThemeModeUseCase(get()) }
    factory { SetHydrationUseCase(get()) }
    factory { ObserveLatestWeightUseCase(get()) }
    factory { ObserveGoalWeightUseCase(get()) }
    factory { SetGoalWeightUseCase(get()) }
}
