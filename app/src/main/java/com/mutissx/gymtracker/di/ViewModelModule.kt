package com.mutissx.gymtracker.di

import com.mutissx.gymtracker.presentation.history.HistoryViewModel
import com.mutissx.gymtracker.presentation.progress.ProgressViewModel
import com.mutissx.gymtracker.presentation.theme.ThemeViewModel
import com.mutissx.gymtracker.presentation.today.TodayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TodayViewModel(get(), get(), get(), get(), get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { ProgressViewModel(get(), get(), get(), get()) }
    viewModel { ThemeViewModel(get(), get()) }
}
