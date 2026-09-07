package com.mutissx.gymtracker

import android.app.Application
import com.mutissx.gymtracker.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GymTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GymTrackerApp)
            modules(appModules)
        }
    }
}
