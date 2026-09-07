package com.mutissx.gymtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mutissx.gymtracker.data.local.entity.ExerciseEntryEntity
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseEntryDao {
    @Insert
    suspend fun insert(entity: ExerciseEntryEntity): Long

    @Query("DELETE FROM exercise_entries WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM exercise_entries WHERE date = :date ORDER BY createdAt ASC")
    fun observeForDate(date: LocalDate): Flow<List<ExerciseEntryEntity>>

    @Query("SELECT * FROM exercise_entries ORDER BY date DESC, createdAt ASC")
    fun observeAll(): Flow<List<ExerciseEntryEntity>>
}
