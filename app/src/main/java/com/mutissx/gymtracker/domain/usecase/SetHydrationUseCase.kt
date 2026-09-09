package com.mutissx.gymtracker.domain.usecase

import com.mutissx.gymtracker.domain.repository.HydrationRepository
import java.time.LocalDate

class SetHydrationUseCase(
    private val hydrationRepository: HydrationRepository
) {
    suspend operator fun invoke(date: LocalDate, hydrated: Boolean) {
        hydrationRepository.setHydrated(date, hydrated)
    }
}
