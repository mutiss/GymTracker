package com.mutissx.gymtracker.data.repository

import com.mutissx.gymtracker.data.local.dao.HydrationEntryDao
import com.mutissx.gymtracker.data.local.entity.HydrationEntryEntity
import com.mutissx.gymtracker.data.mapper.toDomain
import com.mutissx.gymtracker.domain.model.HydrationEntry
import com.mutissx.gymtracker.domain.repository.HydrationRepository
import java.time.LocalDate
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HydrationRepositoryImpl(
    private val dao: HydrationEntryDao
) : HydrationRepository {
    override suspend fun setHydrated(date: LocalDate, hydrated: Boolean) {
        dao.upsert(
            HydrationEntryEntity(
                date = date,
                hydrated = hydrated,
                updatedAt = LocalDateTime.now()
            )
        )
    }

    override fun observeHydrationForDate(date: LocalDate): Flow<HydrationEntry?> =
        dao.observeForDate(date).map { it?.toDomain() }

    override fun observeAllHydration(): Flow<List<HydrationEntry>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }
}
