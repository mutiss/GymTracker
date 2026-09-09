package com.mutissx.gymtracker.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.mutissx.gymtracker.domain.repository.GoalWeightRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class GoalWeightRepositoryImpl(
    context: Context
) : GoalWeightRepository {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun observeGoalWeight(): Flow<Double?> = callbackFlow {
        fun currentGoal(): Double? =
            if (prefs.contains(KEY_GOAL_WEIGHT)) prefs.getFloat(KEY_GOAL_WEIGHT, 0f).toDouble() else null

        trySend(currentGoal())

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == KEY_GOAL_WEIGHT) trySend(currentGoal())
        }
        prefs.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    override suspend fun setGoalWeight(weightKg: Double) {
        prefs.edit().putFloat(KEY_GOAL_WEIGHT, weightKg.toFloat()).apply()
    }

    private companion object {
        const val PREFS_NAME = "goal_prefs"
        const val KEY_GOAL_WEIGHT = "goal_weight_kg"
    }
}
