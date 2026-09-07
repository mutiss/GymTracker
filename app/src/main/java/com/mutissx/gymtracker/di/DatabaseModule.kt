package com.mutissx.gymtracker.di

import androidx.room.Room
import com.mutissx.gymtracker.data.local.GymTrackerDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            GymTrackerDatabase::class.java,
            "gymtracker.db"
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }
    single { get<GymTrackerDatabase>().exerciseEntryDao() }
    single { get<GymTrackerDatabase>().weightEntryDao() }
}
