package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.model.ThemeMode
import com.mutissx.gymtracker.domain.repository.ThemePreferenceRepository
import kotlinx.coroutines.flow.Flow

class ObserveThemeModeUseCase(
    private val themePreferenceRepository: ThemePreferenceRepository
) {
    operator fun invoke(): Flow<ThemeMode> = themePreferenceRepository.observeThemeMode()
}
