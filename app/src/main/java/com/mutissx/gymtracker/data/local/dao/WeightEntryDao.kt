package com.mutissx.gymtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mutissx.gymtracker.data.local.entity.WeightEntryEntity
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightEntryDao {
    @Upsert
    suspend fun upsert(entity: WeightEntryEntity)

    @Query("SELECT * FROM weight_entries WHERE date = :date LIMIT 1")
    fun observeForDate(date: LocalDate): Flow<WeightEntryEntity?>

    @Query("SELECT * FROM weight_entries ORDER BY date DESC LIMIT 1")
    fun observeLatest(): Flow<WeightEntryEntity?>

    @Query("SELECT * FROM weight_entries ORDER BY date ASC")
    fun observeAll(): Flow<List<WeightEntryEntity>>

    @Query("SELECT * FROM weight_entries WHERE date BETWEEN :start AND :end ORDER BY date ASC")
    fun observeRange(start: LocalDate, end: LocalDate): Flow<List<WeightEntryEntity>>
}
