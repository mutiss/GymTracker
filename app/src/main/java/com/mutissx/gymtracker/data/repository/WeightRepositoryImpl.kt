package com.mutissx.gymtracker.data.repository

import com.mutissx.gymtracker.data.local.dao.WeightEntryDao
import com.mutissx.gymtracker.data.local.entity.WeightEntryEntity
import com.mutissx.gymtracker.data.mapper.toDomain
import com.mutissx.gymtracker.domain.model.WeightEntry
import com.mutissx.gymtracker.domain.repository.WeightRepository
import java.time.LocalDate
import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeightRepositoryImpl(
    private val dao: WeightEntryDao
) : WeightRepository {
    override suspend fun upsertWeight(date: LocalDate, weightKg: Double) {
        dao.upsert(
            WeightEntryEntity(
                date = date,
                weightKg = weightKg,
                updatedAt = LocalDateTime.now()
            )
        )
    }

    override fun observeWeightForDate(date: LocalDate): Flow<WeightEntry?> =
        dao.observeForDate(date).map { it?.toDomain() }

    override fun observeAllWeights(): Flow<List<WeightEntry>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override fun observeWeightsInRange(start: LocalDate, end: LocalDate): Flow<List<WeightEntry>> =
        dao.observeRange(start, end).map { list -> list.map { it.toDomain() } }
}
