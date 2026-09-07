package com.mutissx.gymtracker.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.mutissx.gymtracker.domain.model.ThemeMode
import com.mutissx.gymtracker.domain.repository.ThemePreferenceRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ThemePreferenceRepositoryImpl(
    context: Context
) : ThemePreferenceRepository {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun observeThemeMode(): Flow<ThemeMode> = callbackFlow {
        fun currentMode(): ThemeMode =
            runCatching { ThemeMode.valueOf(prefs.getString(KEY_THEME_MODE, null) ?: ThemeMode.SYSTEM.name) }
                .getOrDefault(ThemeMode.SYSTEM)

        trySend(currentMode())

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == KEY_THEME_MODE) trySend(currentMode())
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
    }

    private companion object {
        const val PREFS_NAME = "theme_prefs"
        const val KEY_THEME_MODE = "theme_mode"
    }
}
