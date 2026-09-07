package com.mutissx.gymtracker.data.repository

import com.mutissx.gymtracker.data.local.dao.ExerciseEntryDao
import com.mutissx.gymtracker.data.mapper.toDomain
import com.mutissx.gymtracker.data.mapper.toEntity
import com.mutissx.gymtracker.domain.model.ExerciseEntry
import com.mutissx.gymtracker.domain.repository.ExerciseRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExerciseRepositoryImpl(
    private val dao: ExerciseEntryDao
) : ExerciseRepository {
    override suspend fun addEntry(entry: ExerciseEntry): Long = dao.insert(entry.toEntity())

    override suspend fun deleteEntry(id: Long) = dao.deleteById(id)

    override fun observeEntriesForDate(date: LocalDate): Flow<List<ExerciseEntry>> =
        dao.observeForDate(date).map { list -> list.map { it.toDomain() } }

    override fun observeAllEntries(): Flow<List<ExerciseEntry>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }
}
