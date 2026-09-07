package com.mutissx.gymtracker.di

import com.mutissx.gymtracker.data.repository.ExerciseRepositoryImpl
import com.mutissx.gymtracker.data.repository.ThemePreferenceRepositoryImpl
import com.mutissx.gymtracker.data.repository.WeightRepositoryImpl
import com.mutissx.gymtracker.domain.repository.ExerciseRepository
import com.mutissx.gymtracker.domain.repository.ThemePreferenceRepository
import com.mutissx.gymtracker.domain.repository.WeightRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<ExerciseRepository> { ExerciseRepositoryImpl(get()) }
    single<WeightRepository> { WeightRepositoryImpl(get()) }
    single<ThemePreferenceRepository> { ThemePreferenceRepositoryImpl(androidContext()) }
}
