package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.model.ThemeMode
import com.mutissx.gymtracker.domain.repository.ThemePreferenceRepository

class SetThemeModeUseCase(
    private val themePreferenceRepository: ThemePreferenceRepository
) {
    suspend operator fun invoke(mode: ThemeMode) {
        themePreferenceRepository.setThemeMode(mode)
    }
}
