package com.mutissx.gymtracker.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "exercise_entries", indices = [Index(value = ["date"])])
data class ExerciseEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val date: LocalDate,
    val category: String,
    val value: Double,
    val unit: String,
    val createdAt: LocalDateTime
)
