package com.mutissx.gymtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mutissx.gymtracker.data.local.entity.HydrationEntryEntity
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

@Dao
interface HydrationEntryDao {
    @Upsert
    suspend fun upsert(entity: HydrationEntryEntity)

    @Query("SELECT * FROM hydration_entries WHERE date = :date LIMIT 1")
    fun observeForDate(date: LocalDate): Flow<HydrationEntryEntity?>

    @Query("SELECT * FROM hydration_entries ORDER BY date ASC")
    fun observeAll(): Flow<List<HydrationEntryEntity>>
}
